package com.javangarda.fantacalcio.mail;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.javangarda.fantacalcio.mail.application.gateway.CommandBus;
import com.javangarda.fantacalcio.mail.application.gateway.commands.SendMailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.impl.QueueDrivenCommandBus;
import com.javangarda.fantacalcio.mail.application.internal.*;
import com.javangarda.fantacalcio.mail.application.internal.impl.DefaultMailDataPreparator;
import com.javangarda.fantacalcio.mail.application.internal.impl.SimpleLocaleProvider;
import com.javangarda.fantacalcio.mail.application.internal.impl.SimpleSentEmailFactory;
import com.javangarda.fantacalcio.mail.application.internal.impl.TransactionalSentMailService;
import com.javangarda.fantacalcio.mail.application.internal.saga.CommandHandler;
import com.javangarda.fantacalcio.mail.application.internal.saga.EventHandler;
import com.javangarda.fantacalcio.mail.application.internal.saga.MailEventPublisher;
import com.javangarda.fantacalcio.mail.application.internal.saga.impl.DefaultEventHandler;
import com.javangarda.fantacalcio.mail.application.internal.saga.impl.EventDrivenCommandHandler;
import com.javangarda.fantacalcio.mail.application.internal.storage.SentEmailRepository;
import com.javangarda.fantacalcio.mail.infrastructure.adapter.income.messaging.DefaultMailDataQueue;
import com.javangarda.fantacalcio.mail.infrastructure.adapter.income.messaging.Events;
import com.javangarda.fantacalcio.mail.infrastructure.adapter.income.messaging.UserExternalEventHandler;
import com.javangarda.fantacalcio.mail.infrastructure.adapter.outcome.mail.DefaultEmailSender;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.endpoint.PollingConsumer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableEurekaClient
@EnableBinding(Events.class)
@EnableIntegration
@IntegrationComponentScan(basePackages={"com.javangarda.fantacalcio.mail.application.internal.saga"})
@EnableScheduling
public class FantacalcioMailApplication implements AsyncConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(FantacalcioMailApplication.class, args);
	}

	@Bean
	public QueueChannel mailDataQueueChannel() {
		return new QueueChannel(100);
	}

	@Bean
	public LocaleProvider localeProvider(){
		return new SimpleLocaleProvider();
	}

	@Bean
	@RefreshScope
	public MailDataPreparator mailDataPreparator(final MessageSource messageSource, final TemplateEngine templateEngine,
												 final LocaleProvider localeProvider,
												 @Value("${fantacalcio.webapp.mainurl}") String applicationUrl,
												 @Value("${fantacalcio.mail.addresses.contact}") String supportContact){
		return new DefaultMailDataPreparator(messageSource, templateEngine, localeProvider, applicationUrl, supportContact);
	}

	@Bean
	public EmailSender emailSender(final JavaMailSender javaMailSender, @Value("${spring.mail.username}") String mailHostname){
		return new DefaultEmailSender(javaMailSender, mailHostname);
	}

	@Bean
	public MessageChannel mailSentChannel(){
		return new PublishSubscribeChannel(getAsyncExecutor());
	}

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(5);
		executor.setQueueCapacity(5);
		executor.initialize();
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}

	@Bean
	public CommandBus commandBus(final MailDataPreparator mailDataPreparator, final MailDataQueue mailDataQueue){
		return new QueueDrivenCommandBus(mailDataPreparator, mailDataQueue);
	}

	@Bean
	public SentEmailFactory sentEmailFactory(){
		return new SimpleSentEmailFactory();
	}

	@Bean
	public ObjectMapper objectMapper(){
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new Jdk8Module());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}

	@Bean
	public UserExternalEventHandler userExternalEventHandler(CommandBus commandBus) {
		return new UserExternalEventHandler(commandBus);
	}

	@Bean
	public CommandHandler commandHandler(EmailSender emailSender, MailEventPublisher mailEventPublisher) {
		return new EventDrivenCommandHandler(emailSender, mailEventPublisher);
	}

	@Bean
	public MailDataQueue mailDataQueue(QueueChannel mailDataQueueChannel) {
		return new DefaultMailDataQueue(mailDataQueueChannel);
	}

	@Bean
	public PollingConsumer mailDataPollingConsumer(QueueChannel mailDataQueueChannel, CommandHandler commandHandler){
		PollingConsumer pollingConsumer = new PollingConsumer(mailDataQueueChannel, message -> {
			SendMailCommand sendMailCommand = (SendMailCommand) message.getPayload();
			commandHandler.handle(sendMailCommand);
		});
		pollingConsumer.setMaxMessagesPerPoll(1);
		pollingConsumer.setReceiveTimeout(10);
		pollingConsumer.setAutoStartup(true);
		return pollingConsumer;
	}

	@Bean
	public EventHandler eventHandler(SentMailService sentMailService) {
		return new DefaultEventHandler(sentMailService);
	}

	@Bean
	public SentMailService sentMailService(SentEmailFactory sentMailFactory, SentEmailRepository sentEmailRepository) {
		return new TransactionalSentMailService(sentEmailRepository, sentMailFactory);
	}

	@Bean
	public ClassLoaderTemplateResolver emailTemplateResolver(){
		ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
		emailTemplateResolver.setPrefix("/templates/");
		emailTemplateResolver.setSuffix(".html");
		emailTemplateResolver.setTemplateMode("HTML5");
		emailTemplateResolver.setCharacterEncoding("UTF-8");
		emailTemplateResolver.setOrder(1);

		return emailTemplateResolver;
	}
}

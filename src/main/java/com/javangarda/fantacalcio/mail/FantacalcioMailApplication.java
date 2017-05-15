package com.javangarda.fantacalcio.mail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.javangarda.fantacalcio.mail.application.data.MailData;
import com.javangarda.fantacalcio.mail.application.gateway.MailGateway;
import com.javangarda.fantacalcio.mail.application.internal.MailDataQueue;
import com.javangarda.fantacalcio.mail.application.internal.impl.DefaultMailDataPreparator;
import com.javangarda.fantacalcio.mail.application.gateway.impl.QueueDrivenMailGateway;
import com.javangarda.fantacalcio.mail.application.internal.EmailSender;
import com.javangarda.fantacalcio.mail.application.internal.MailDataPreparator;
import com.javangarda.fantacalcio.mail.infrastructure.port.adapter.messaging.DefaultMailDataQueue;
import com.javangarda.fantacalcio.mail.infrastructure.port.adapter.mail.DefaultEmailSender;
import com.javangarda.fantacalcio.mail.infrastructure.port.adapter.messaging.Events;
import com.javangarda.fantacalcio.mail.infrastructure.port.adapter.messaging.MessageHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.endpoint.PollingConsumer;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(Events.class)
public class FantacalcioMailApplication {

	public static void main(String[] args) {
		SpringApplication.run(FantacalcioMailApplication.class, args);
	}

	@Bean
	public QueueChannel mailDataQueueChannel() {
		return new QueueChannel(100);
	}

	@Bean
	@RefreshScope
	public MailDataPreparator mailDataPreparator(final MessageSource messageSource, final TemplateEngine templateEngine,
												 @Value("${fantacalcio.webapp.mainurl}") String applicationUrl,
												 @Value("${fantacalcio.mail.addresses.contact}") String supportContact){
		return new DefaultMailDataPreparator(messageSource, templateEngine, applicationUrl, supportContact);
	}

	@Bean
	public EmailSender emailSender(final JavaMailSender javaMailSender, @Value("${spring.mail.username}") String mailHostname){
		return new DefaultEmailSender(javaMailSender, mailHostname);
	}

	@Bean
	public MailGateway mailGateway(final MailDataPreparator mailDataPreparator, final MailDataQueue mailDataQueue){
		return new QueueDrivenMailGateway(mailDataPreparator, mailDataQueue);
	}

	@Bean
	public ObjectMapper objectMapper(){
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new Jdk8Module());
		return objectMapper;
	}

	@Bean
	public MessageHandler messageHandler(MailGateway mailGateway) {
		return new MessageHandler(mailGateway);
	}

	@Bean
	public MailDataQueue mailDataQueue(QueueChannel mailDataQueueChannel) {
		return new DefaultMailDataQueue(mailDataQueueChannel);
	}

	@Bean
	public PollingConsumer mailDataPollingConsumer(QueueChannel mailDataQueueChannel, EmailSender emailSender){
		PollingConsumer pollingConsumer = new PollingConsumer(mailDataQueueChannel, message -> {
			MailData mailData = (MailData) message.getPayload();
			emailSender.send(mailData);
		});
		pollingConsumer.setMaxMessagesPerPoll(1);
		pollingConsumer.setReceiveTimeout(10);
		pollingConsumer.setAutoStartup(true);
		return pollingConsumer;
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

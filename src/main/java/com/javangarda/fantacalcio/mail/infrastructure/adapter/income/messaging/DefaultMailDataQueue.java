package com.javangarda.fantacalcio.mail.infrastructure.adapter.income.messaging;


import com.javangarda.fantacalcio.mail.application.gateway.commands.SendMailCommand;
import com.javangarda.fantacalcio.mail.application.internal.MailDataQueue;
import lombok.AllArgsConstructor;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@AllArgsConstructor
public class DefaultMailDataQueue implements MailDataQueue {

    private final QueueChannel mailDataQueueChannel;

    @Override
    public void add(SendMailCommand sendMailCommand) {
        Message<SendMailCommand> message = MessageBuilder.withPayload(sendMailCommand).build();
        mailDataQueueChannel.send(message);
    }
}

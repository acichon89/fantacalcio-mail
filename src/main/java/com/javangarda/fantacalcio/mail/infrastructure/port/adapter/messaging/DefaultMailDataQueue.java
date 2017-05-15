package com.javangarda.fantacalcio.mail.infrastructure.port.adapter.messaging;


import com.javangarda.fantacalcio.mail.application.data.MailData;
import com.javangarda.fantacalcio.mail.application.internal.MailDataQueue;
import lombok.AllArgsConstructor;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@AllArgsConstructor
public class DefaultMailDataQueue implements MailDataQueue {

    private final QueueChannel mailDataQueueChannel;

    @Override
    public void add(MailData mailData) {
        Message<MailData> message = MessageBuilder.withPayload(mailData).build();
        mailDataQueueChannel.send(message);
    }
}

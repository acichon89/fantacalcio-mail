package com.javangarda.fantacalcio.mail.application.internal.saga;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MailEventPublisher {

    @Gateway(requestChannel = "mailSentChannel")
    void publishMailSentEvent(MailSentEvent event);
}

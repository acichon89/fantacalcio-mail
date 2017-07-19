package com.javangarda.fantacalcio.mail.application.internal.saga.impl;


import com.javangarda.fantacalcio.mail.application.internal.saga.MailSentEvent;
import com.javangarda.fantacalcio.mail.application.internal.saga.EventHandler;
import com.javangarda.fantacalcio.mail.application.internal.SentMailService;
import lombok.AllArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;

@AllArgsConstructor
public class DefaultEventHandler implements EventHandler{

    private final SentMailService sentMailService;

    @Override
    @ServiceActivator(inputChannel = "mailSentChannel")
    public void handle(MailSentEvent e) {
        sentMailService.store(e.getRecipientEmail(), e.getHtmlContent(), e.getTitle(), e.getSentDateTime());
    }
}

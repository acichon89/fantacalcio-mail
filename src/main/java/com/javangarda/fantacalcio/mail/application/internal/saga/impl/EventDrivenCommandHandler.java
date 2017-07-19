package com.javangarda.fantacalcio.mail.application.internal.saga.impl;

import com.javangarda.fantacalcio.mail.application.gateway.commands.SendMailCommand;
import com.javangarda.fantacalcio.mail.application.internal.saga.MailSentEvent;
import com.javangarda.fantacalcio.mail.application.internal.saga.CommandHandler;
import com.javangarda.fantacalcio.mail.application.internal.EmailSender;
import com.javangarda.fantacalcio.mail.application.internal.saga.MailEventPublisher;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
public class EventDrivenCommandHandler implements CommandHandler {

    private final EmailSender emailSender;
    private final MailEventPublisher mailEventPublisher;

    @Override
    public void handle(SendMailCommand command) {
        emailSender.send(command);
        mailEventPublisher.publishMailSentEvent(MailSentEvent.of(command.getRecipient(), command.getContent(), command.getTitle(), LocalDateTime.now()));
    }
}

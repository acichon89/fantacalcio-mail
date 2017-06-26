package com.javangarda.fantacalcio.mail.application.gateway.impl;


import com.javangarda.fantacalcio.mail.application.gateway.commands.ChangeEmailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.commands.ConfirmEmailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.CommandBus;
import com.javangarda.fantacalcio.mail.application.internal.MailDataPreparator;
import com.javangarda.fantacalcio.mail.application.internal.MailDataQueue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SimpleCommandBus implements CommandBus {
    private final MailDataPreparator mailDataPreparator;
    private final MailDataQueue mailDataQueue;

    @Override
    public void sendRegistrationConfirmationMail(ConfirmEmailCommand command) {
        mailDataQueue.add(mailDataPreparator.prepare(command));
    }

    @Override
    public void sendConfirmationEmailMail(ChangeEmailCommand command) {
        mailDataQueue.add(mailDataPreparator.prepare(command));
    }
}

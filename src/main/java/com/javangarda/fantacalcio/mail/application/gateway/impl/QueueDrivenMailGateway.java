package com.javangarda.fantacalcio.mail.application.gateway.impl;

import com.javangarda.fantacalcio.mail.application.data.MailData;
import com.javangarda.fantacalcio.mail.application.data.commands.ConfirmEmailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.MailGateway;
import com.javangarda.fantacalcio.mail.application.internal.EmailSender;
import com.javangarda.fantacalcio.mail.application.internal.MailDataPreparator;
import com.javangarda.fantacalcio.mail.application.internal.MailDataQueue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QueueDrivenMailGateway implements MailGateway{

    private final MailDataPreparator mailDataPreparator;
    private final MailDataQueue mailDataQueue;

    @Override
    public void sendRegistrationConfirmationMail(ConfirmEmailCommand command) {
        MailData mailData = mailDataPreparator.prepare(command);
        mailDataQueue.add(mailData);
    }

}

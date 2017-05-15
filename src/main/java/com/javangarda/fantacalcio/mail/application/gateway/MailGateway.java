package com.javangarda.fantacalcio.mail.application.gateway;


import com.javangarda.fantacalcio.mail.application.data.commands.ConfirmEmailCommand;

public interface MailGateway {

    void sendRegistrationConfirmationMail(ConfirmEmailCommand command);
}

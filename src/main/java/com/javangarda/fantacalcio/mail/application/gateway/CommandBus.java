package com.javangarda.fantacalcio.mail.application.gateway;


import com.javangarda.fantacalcio.mail.application.gateway.commands.ChangeEmailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.commands.ConfirmEmailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.commands.ResetPasswordEmailCommand;

public interface CommandBus {

    void sendRegistrationConfirmationMail(ConfirmEmailCommand command);
    void sendConfirmationEmailMail(ChangeEmailCommand command);
    void sendResetPasswordEmailMail(ResetPasswordEmailCommand command);

}

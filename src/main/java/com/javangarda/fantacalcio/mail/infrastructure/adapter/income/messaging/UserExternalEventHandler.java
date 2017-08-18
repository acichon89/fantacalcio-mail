package com.javangarda.fantacalcio.mail.infrastructure.adapter.income.messaging;

import com.javangarda.fantacalcio.mail.application.gateway.CommandBus;
import com.javangarda.fantacalcio.mail.application.gateway.commands.ChangeEmailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.commands.ConfirmEmailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.commands.ResetPasswordEmailCommand;
import com.javangarda.fantacalcio.mail.infrastructure.adapter.income.messaging.model.UserAttemptedToChangeEmailEvent;
import com.javangarda.fantacalcio.mail.infrastructure.adapter.income.messaging.model.UserForgotPasswordEvent;
import com.javangarda.fantacalcio.mail.infrastructure.adapter.income.messaging.model.UserRegisteredEvent;
import lombok.AllArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;

import java.util.Optional;

@AllArgsConstructor
public class UserExternalEventHandler {

    private CommandBus commandBus;

    @ServiceActivator(inputChannel = Events.SEND_CONFIRMATION_MAIL_INPUT)
    public void handle(UserRegisteredEvent event){
        ConfirmEmailCommand command = ConfirmEmailCommand.of(event.getEmail(), event.getConfirmationToken(), Optional.ofNullable(event.getEmailLocale()));
        commandBus.sendRegistrationConfirmationMail(command);
    }

    @ServiceActivator(inputChannel = Events.SEND_RESETPASS_MAIL_INPUT)
    public void handle(UserForgotPasswordEvent event){
        ResetPasswordEmailCommand command = ResetPasswordEmailCommand.of(event.getEmail(), event.getFullName(), event.getResetPasswordToken(), Optional.ofNullable(event.getEmailLocale()));
        commandBus.sendResetPasswordEmailMail(command);
    }

    @ServiceActivator(inputChannel = Events.SEND_CHANGE_EMAIL_INPUT)
    public void handleUserAttemptedToChangeEmailEvent(UserAttemptedToChangeEmailEvent event) {
        commandBus.sendConfirmationEmailMail(ChangeEmailCommand.of(
                event.getFullName()+" ("+event.getEmail()+")",
                event.getNewEmail(),
                event.getConfirmationToken(),
                Optional.ofNullable(event.getEmailLocale())
        ));
    }

}

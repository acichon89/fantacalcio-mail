package com.javangarda.fantacalcio.mail.infrastructure.port.adapter.messaging;

import com.javangarda.fantacalcio.mail.application.gateway.commands.ChangeEmailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.commands.ConfirmEmailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.CommandBus;
import lombok.AllArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;

import java.util.Optional;

@AllArgsConstructor
public class MessageHandler {

    private CommandBus commandBus;

    @ServiceActivator(inputChannel = Events.SEND_CONFIRMATION_MAIL_INPUT)
    public void handleConfirmEmailCommandEvent(ConfirmEmailCommand command){
        commandBus.sendRegistrationConfirmationMail(command);
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

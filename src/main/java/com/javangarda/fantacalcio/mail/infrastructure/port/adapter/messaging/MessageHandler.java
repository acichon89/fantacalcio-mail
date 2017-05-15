package com.javangarda.fantacalcio.mail.infrastructure.port.adapter.messaging;

import com.javangarda.fantacalcio.mail.application.data.commands.ConfirmEmailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.MailGateway;
import lombok.AllArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;

@AllArgsConstructor
public class MessageHandler {

    private MailGateway mailGateway;

    @ServiceActivator(inputChannel = Events.SEND_CONFIRMATION_MAIL_INPUT)
    public void handleConfirmEmailCommandEvent(ConfirmEmailCommand command){
        mailGateway.sendRegistrationConfirmationMail(command);
    }
}

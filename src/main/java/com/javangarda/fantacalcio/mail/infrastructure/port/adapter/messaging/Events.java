package com.javangarda.fantacalcio.mail.infrastructure.port.adapter.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Events {
    String SEND_CONFIRMATION_MAIL_INPUT = "sendConfirmationMailChannel";

    @Input(SEND_CONFIRMATION_MAIL_INPUT)
    SubscribableChannel sendConfirmationMailChannel();
}

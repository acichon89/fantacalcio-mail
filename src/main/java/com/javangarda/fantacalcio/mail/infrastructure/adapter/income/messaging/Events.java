package com.javangarda.fantacalcio.mail.infrastructure.adapter.income.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Events {
    String SEND_CONFIRMATION_MAIL_INPUT = "sendConfirmationMailChannel";

    @Input(SEND_CONFIRMATION_MAIL_INPUT)
    SubscribableChannel sendConfirmationMailChannel();

    String SEND_CHANGE_EMAIL_INPUT = "userAttemptedToChangeEmailChannel";

    @Input(SEND_CHANGE_EMAIL_INPUT)
    SubscribableChannel sendChangeEmailInput();

    String SEND_RESETPASS_MAIL_INPUT = "userForgotPasswordChannel";

    @Input(SEND_RESETPASS_MAIL_INPUT)
    SubscribableChannel sendResetPasswordChannel();
}

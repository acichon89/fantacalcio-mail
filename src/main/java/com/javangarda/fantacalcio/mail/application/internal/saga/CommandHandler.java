package com.javangarda.fantacalcio.mail.application.internal.saga;

import com.javangarda.fantacalcio.mail.application.gateway.commands.SendMailCommand;

public interface CommandHandler {
    void handle(SendMailCommand command);
}

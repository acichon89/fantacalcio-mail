package com.javangarda.fantacalcio.mail.application.internal.saga;


public interface EventHandler {
    void handle(MailSentEvent mailSentEvent);
}

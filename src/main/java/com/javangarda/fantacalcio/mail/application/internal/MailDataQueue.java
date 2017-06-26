package com.javangarda.fantacalcio.mail.application.internal;


import com.javangarda.fantacalcio.mail.application.gateway.commands.SendMailCommand;

public interface MailDataQueue {
    void add(SendMailCommand sendMailCommand);
}

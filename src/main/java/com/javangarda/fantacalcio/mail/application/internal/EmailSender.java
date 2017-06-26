package com.javangarda.fantacalcio.mail.application.internal;


import com.javangarda.fantacalcio.mail.application.gateway.commands.SendMailCommand;

public interface EmailSender {

    void send(SendMailCommand sendMailCommand);
}

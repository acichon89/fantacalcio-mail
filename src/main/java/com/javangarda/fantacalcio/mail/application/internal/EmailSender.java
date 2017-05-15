package com.javangarda.fantacalcio.mail.application.internal;


import com.javangarda.fantacalcio.mail.application.data.MailData;

public interface EmailSender {

    void send(MailData mailData);
}

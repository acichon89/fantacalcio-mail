package com.javangarda.fantacalcio.mail.application.internal;


import com.javangarda.fantacalcio.mail.application.gateway.commands.ResetPasswordEmailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.commands.SendMailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.commands.ChangeEmailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.commands.ConfirmEmailCommand;

public interface MailDataPreparator {
    SendMailCommand prepare(ConfirmEmailCommand command);
    SendMailCommand prepare(ChangeEmailCommand command);
    SendMailCommand prepare(ResetPasswordEmailCommand command);
}

package com.javangarda.fantacalcio.mail.application.internal;


import com.javangarda.fantacalcio.mail.application.data.MailData;
import com.javangarda.fantacalcio.mail.application.data.commands.ConfirmEmailCommand;

public interface MailDataPreparator {
    MailData prepare(ConfirmEmailCommand command);
}

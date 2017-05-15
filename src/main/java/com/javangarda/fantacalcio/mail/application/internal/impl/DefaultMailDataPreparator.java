package com.javangarda.fantacalcio.mail.application.internal.impl;


import com.javangarda.fantacalcio.mail.application.data.MailData;
import com.javangarda.fantacalcio.mail.application.data.commands.ConfirmEmailCommand;
import com.javangarda.fantacalcio.mail.application.internal.MailDataPreparator;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.MessageSource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@AllArgsConstructor
public class DefaultMailDataPreparator implements MailDataPreparator {

    private final MessageSource messageSource;
    private final TemplateEngine templateEngine;
    private final String applicationUrl;
    private final String supportContact;
    private static final Locale DEFAULT_LOCALE = new Locale("en","GB");


    @Override
    public MailData prepare(ConfirmEmailCommand command) {
        Locale locale = command.getLocale().orElse(DEFAULT_LOCALE);
        String title = messageSource.getMessage("activationMail.title", null, locale);
        Context context = new Context();
        context.setVariable("email", command.getEmail());
        context.setVariable("confirmationUrl", applicationUrl + "/confirmMail?token="+command.getConfirmationToken());
        context.setVariable("supportContact", supportContact);
        return MailData.of(command.getEmail(), title, templateEngine.process("confirmation_mail", context));
    }
}

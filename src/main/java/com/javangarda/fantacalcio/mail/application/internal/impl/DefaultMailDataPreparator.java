package com.javangarda.fantacalcio.mail.application.internal.impl;


import com.javangarda.fantacalcio.mail.application.gateway.commands.SendMailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.commands.ChangeEmailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.commands.ConfirmEmailCommand;
import com.javangarda.fantacalcio.mail.application.internal.LocaleProvider;
import com.javangarda.fantacalcio.mail.application.internal.MailDataPreparator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

@AllArgsConstructor
@Slf4j
public class DefaultMailDataPreparator implements MailDataPreparator {

    private final MessageSource messageSource;
    private final TemplateEngine templateEngine;
    private final LocaleProvider localeProvider;
    private final String applicationUrl;
    private final String supportContact;


    @Override
    public SendMailCommand prepare(ConfirmEmailCommand command) {
        Locale locale = command.getEmailLocale().filter(localeProvider::support).orElse(localeProvider.defaultLocale());
        String title = messageSource.getMessage("activationMail.title", null, locale);
        Context context = createContext(locale);
        context.setVariable("email", command.getEmail());
        context.setVariable("confirmationUrl", applicationUrl + "/confirmMail?token="+command.getConfirmationToken()+"&email="+ encode(command.getEmail()));
        context.setVariable("supportContact", supportContact);
        return SendMailCommand.of(command.getEmail(), title, templateEngine.process("confirmation_mail", context));
    }

    @Override
    public SendMailCommand prepare(ChangeEmailCommand command) {
        Locale locale = command.getEmailLocale().filter(localeProvider::support).orElse(localeProvider.defaultLocale());
        String title = messageSource.getMessage("changeEmailMail.title", null, locale);
        Context context = createContext(locale);
        context.setVariable("userName", command.getUserName());
        context.setVariable("newEmail", command.getNewEmail());
        context.setVariable("changeMailUrl", applicationUrl +"/changeMail?token"+command.getConfirmationToken());
        return SendMailCommand.of(command.getNewEmail(), title, templateEngine.process("change_mail", context));
    }

    private Context createContext(Locale locale){
        Context context = new Context();
        context.setLocale(locale);
        return context;
    }

    private String encode(String value){
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
           log.error("Error while encoding value '"+value+"'", e);
        }
        return "";
    }
}

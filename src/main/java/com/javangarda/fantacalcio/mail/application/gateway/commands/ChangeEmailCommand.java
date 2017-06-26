package com.javangarda.fantacalcio.mail.application.gateway.commands;

import lombok.Value;

import java.util.Locale;
import java.util.Optional;

@Value(staticConstructor = "of")
public class ChangeEmailCommand {
    private String userName;
    private String newEmail;
    private String confirmationToken;
    private Optional<Locale> emailLocale;
}

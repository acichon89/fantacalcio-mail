package com.javangarda.fantacalcio.mail.application.gateway.commands;

import lombok.Value;

import java.util.Locale;
import java.util.Optional;

@Value(staticConstructor = "of")
public class ConfirmEmailCommand {

    private String email;
    private String confirmationToken;
    private Optional<Locale> emailLocale;
}

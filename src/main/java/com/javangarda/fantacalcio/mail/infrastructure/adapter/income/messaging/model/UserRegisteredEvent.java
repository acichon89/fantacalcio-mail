package com.javangarda.fantacalcio.mail.infrastructure.adapter.income.messaging.model;

import lombok.Value;

import java.util.Locale;

@Value(staticConstructor = "of")
public class UserRegisteredEvent {
    private String fullName;
    private String email;
    private String confirmationToken;
    private Locale emailLocale;
}

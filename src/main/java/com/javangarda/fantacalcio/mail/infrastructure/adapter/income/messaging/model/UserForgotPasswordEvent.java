package com.javangarda.fantacalcio.mail.infrastructure.adapter.income.messaging.model;

import lombok.Value;

import java.util.Locale;

@Value(staticConstructor = "of")
public class UserForgotPasswordEvent {
    private String fullName;
    private String email;
    private String resetPasswordToken;
    private Locale emailLocale;
}
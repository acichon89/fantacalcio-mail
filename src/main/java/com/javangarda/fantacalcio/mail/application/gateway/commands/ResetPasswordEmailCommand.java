package com.javangarda.fantacalcio.mail.application.gateway.commands;


import lombok.Value;

import java.util.Locale;
import java.util.Optional;

@Value(staticConstructor = "of")
public class ResetPasswordEmailCommand {
    private String email;
    private String fullName;
    private String resetPasswordToken;
    private Optional<Locale> emailLocale;
}

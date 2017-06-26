package com.javangarda.fantacalcio.mail.infrastructure.port.adapter.messaging;


import lombok.Value;

import java.util.Locale;

@Value(staticConstructor = "of")
public class UserAttemptedToChangeEmailEvent {
    private String id;
    private String email;
    private String fullName;
    private String newEmail;
    private String confirmationToken;
    private Locale emailLocale;
}


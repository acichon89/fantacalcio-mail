package com.javangarda.fantacalcio.mail.application.data.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.Locale;
import java.util.Optional;

@AllArgsConstructor
@Value
public class ConfirmEmailCommand {

    private String email;
    private String confirmationToken;
    private Optional<Locale> locale;
}

package com.javangarda.fantacalcio.mail.application.internal;


import java.util.Locale;
import java.util.Optional;

public interface LocaleProvider {
    Locale provideLocale(Optional<Locale> commandLocale);
}

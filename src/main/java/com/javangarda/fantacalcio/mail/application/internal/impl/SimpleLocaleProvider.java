package com.javangarda.fantacalcio.mail.application.internal.impl;

import com.javangarda.fantacalcio.mail.application.internal.LocaleProvider;

import java.util.*;

public class SimpleLocaleProvider implements LocaleProvider {

    private static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(new Locale("en", "GB"), new Locale("pl", "PL"));

    @Override
    public Locale provideLocale(Optional<Locale> potentialLocale) {
        return potentialLocale.filter(this::support).orElse(this.defaultLocale());
    }

    private Locale defaultLocale() {
        return SUPPORTED_LOCALES.get(0);
    }

    private boolean support(Locale locale) {
        return SUPPORTED_LOCALES.contains(locale);
    }
}

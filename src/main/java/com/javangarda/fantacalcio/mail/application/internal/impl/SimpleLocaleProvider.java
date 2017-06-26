package com.javangarda.fantacalcio.mail.application.internal.impl;

import com.javangarda.fantacalcio.mail.application.internal.LocaleProvider;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class SimpleLocaleProvider implements LocaleProvider {

    private static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(new Locale("en", "GB"), new Locale("pl", "PL"));

    @Override
    public Locale defaultLocale() {
        return SUPPORTED_LOCALES.get(0);
    }

    @Override
    public boolean support(Locale locale) {
        return SUPPORTED_LOCALES.contains(locale);
    }
}

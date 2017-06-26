package com.javangarda.fantacalcio.mail.application.internal;


import java.util.Locale;

public interface LocaleProvider {
    Locale defaultLocale();
    boolean support(Locale locale);
}

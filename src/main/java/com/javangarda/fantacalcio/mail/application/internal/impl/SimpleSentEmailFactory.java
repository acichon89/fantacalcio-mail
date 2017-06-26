package com.javangarda.fantacalcio.mail.application.internal.impl;


import com.javangarda.fantacalcio.mail.application.internal.SentEmailFactory;
import com.javangarda.fantacalcio.mail.application.internal.storage.SentEmail;

import java.time.Instant;
import java.util.UUID;

public class SimpleSentEmailFactory implements SentEmailFactory {

    @Override
    public SentEmail create(String email, String htmlContent, String title, Instant sentInstant) {
        return new SentEmail(generateId(), email, sentInstant, htmlContent, title);
    }

    protected String generateId(){
        return UUID.randomUUID().toString();
    }
}

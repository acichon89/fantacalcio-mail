package com.javangarda.fantacalcio.mail.application.internal.impl;


import com.javangarda.fantacalcio.mail.application.internal.SentEmailFactory;
import com.javangarda.fantacalcio.mail.application.internal.storage.SentEmail;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class SimpleSentEmailFactory implements SentEmailFactory {

    @Override
    public SentEmail create(String email, String htmlContent, String title, LocalDateTime sentDateTime) {
        return new SentEmail(generateId(), email, sentDateTime, htmlContent, title);
    }

    protected String generateId(){
        return UUID.randomUUID().toString();
    }
}

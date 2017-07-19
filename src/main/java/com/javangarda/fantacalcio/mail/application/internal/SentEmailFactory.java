package com.javangarda.fantacalcio.mail.application.internal;

import com.javangarda.fantacalcio.mail.application.internal.storage.SentEmail;

import java.time.Instant;
import java.time.LocalDateTime;


public interface SentEmailFactory {
    SentEmail create(String email, String htmlContent, String title, LocalDateTime sentDateTime);
}

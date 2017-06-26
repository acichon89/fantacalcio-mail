package com.javangarda.fantacalcio.mail.application.internal;


import java.time.Instant;

public interface SentMailService {
    void store(String email, String htmlContent, String title, Instant sentInstant);
}

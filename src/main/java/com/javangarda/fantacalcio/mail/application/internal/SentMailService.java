package com.javangarda.fantacalcio.mail.application.internal;


import java.time.LocalDateTime;

public interface SentMailService {
    void store(String email, String htmlContent, String title, LocalDateTime sentInstant);
}

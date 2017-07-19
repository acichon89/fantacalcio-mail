package com.javangarda.fantacalcio.mail.application.internal.saga;

import lombok.Value;

import java.time.Instant;
import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class MailSentEvent {
    private String recipientEmail;
    private String htmlContent;
    private String title;
    private LocalDateTime sentDateTime;
}

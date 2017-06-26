package com.javangarda.fantacalcio.mail.application.gateway.data;


import lombok.Data;

import java.time.Instant;

@Data
public class SentEventDTO {
    private String id;
    private String recipientEmail;
    private String htmlContent;
    private Instant sentDateTime;
}

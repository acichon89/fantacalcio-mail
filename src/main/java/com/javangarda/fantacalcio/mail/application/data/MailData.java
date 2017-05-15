package com.javangarda.fantacalcio.mail.application.data;

import lombok.ToString;
import lombok.Value;

import java.util.Map;

@Value(staticConstructor = "of")
@ToString
public class MailData {
    private String recipient;
    private String title;
    private String content;
}

package com.javangarda.fantacalcio.mail.application.gateway.commands;

import lombok.ToString;
import lombok.Value;

@Value(staticConstructor = "of")
@ToString
public class SendMailCommand {
    private String recipient;
    private String title;
    private String content;
}

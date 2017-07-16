package com.javangarda.fantacalcio.mail.application.internal.storage;


import com.javangarda.fantacalcio.commons.entities.DefaultEntity;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "sent_emails")
@ToString
public class SentEmail extends DefaultEntity<String> {

    @Getter
    private String email;
    @Column(name = "sent_date_time")
    @Getter
    private Instant sentDateTime;
    @Getter
    @Column(name = "html_content")
    private String htmlContent;
    @Getter
    private String title;

    public SentEmail() {}

    public SentEmail(String id, String email, Instant sentInstant, String htmlContent, String title) {
        super(id);
        this.email=email;
        this.sentDateTime=sentInstant;
        this.htmlContent=htmlContent;
        this.title=title;
    }
}

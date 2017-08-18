package com.javangarda.fantacalcio.mail.application.internal.impl;

import com.javangarda.fantacalcio.mail.application.internal.SentEmailFactory;
import com.javangarda.fantacalcio.mail.application.internal.SentMailService;
import com.javangarda.fantacalcio.mail.application.internal.storage.SentEmailRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;

@Transactional
@AllArgsConstructor
public class TransactionalSentMailService implements SentMailService {

    private final SentEmailRepository sentEmailRepository;
    private final SentEmailFactory sentEmailFactory;

    @Override
    public void store(String email, String htmlContent, String title, LocalDateTime sentInstant) {
        sentEmailRepository.save(sentEmailFactory.create(email, htmlContent, title, sentInstant));
    }
}

package com.javangarda.fantacalcio.mail.application.internal.impl;

import com.javangarda.fantacalcio.mail.application.internal.SentEmailFactory;
import com.javangarda.fantacalcio.mail.application.internal.SentMailService;
import com.javangarda.fantacalcio.mail.application.internal.storage.SentEmailRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Transactional
@AllArgsConstructor
public class TransactionalSentMailService implements SentMailService {

    private SentEmailRepository sentEmailRepository;
    private SentEmailFactory sentEmailFactory;

    @Override
    public void store(String email, String htmlContent, String title, Instant sentInstant) {
        sentEmailRepository.save(sentEmailFactory.create(email, htmlContent, title, sentInstant));
    }
}

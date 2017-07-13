package com.javangarda.fantacalcio.mail.application.internal.impl;

import com.javangarda.fantacalcio.mail.application.internal.SentEmailFactory;
import com.javangarda.fantacalcio.mail.application.internal.storage.SentEmail;
import com.javangarda.fantacalcio.mail.application.internal.storage.SentEmailRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;

import static org.mockito.Mockito.*;

public class TransactionalSentMailServiceTest {

    private SentEmailRepository sentEmailRepository;
    private SentEmailFactory sentEmailFactory;

    private TransactionalSentMailService transactionalSentMailService;

    @Before
    public void init() {
        sentEmailRepository = mock(SentEmailRepository.class);
        sentEmailFactory = new SimpleSentEmailFactory();

        transactionalSentMailService = new TransactionalSentMailService(sentEmailRepository,sentEmailFactory);
    }

    @Test
    public void should_persist_what_factory_creates() {
        //given:
        //when:
        transactionalSentMailService.store("john@doe.com", "<p>Hello</p>", "hello", Instant.now());
        //then:
        ArgumentCaptor<SentEmail> sentEmailAC = ArgumentCaptor.forClass(SentEmail.class);
        verify(sentEmailRepository).save(sentEmailAC.capture());
        Assertions.assertThat(sentEmailAC.getValue().getEmail()).isEqualTo("john@doe.com");
    }
}
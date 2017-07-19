package com.javangarda.fantacalcio.mail.application.gateway.impl;

import com.javangarda.fantacalcio.mail.application.gateway.data.SentMailDTO;
import com.javangarda.fantacalcio.mail.application.internal.storage.SentEmail;
import com.javangarda.fantacalcio.mail.application.internal.storage.SentEmailRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

public class SimpleQueryFacadeTest {

    private SentEmailRepository sentEmailRepository;

    private SimpleQueryFacade simpleQueryFacade;

    @Before
    public void init(){
        sentEmailRepository = Mockito.mock(SentEmailRepository.class);
        simpleQueryFacade = new SimpleQueryFacade(sentEmailRepository);
    }

    @Test
    public void should_return_what_repo_returns() {
        //given:
        SentEmail sentEmail = new SentEmail("123", "john@doe.com", LocalDateTime.now(), "<p>hello</p>", "Hello!");
        Mockito.when(sentEmailRepository.findById("123")).thenReturn(Optional.of(sentEmail));
        //when:
        Optional<SentMailDTO> mail = simpleQueryFacade.getById("123");
        //then:
        Assert.assertEquals("john@doe.com", mail.get().getRecipientEmail());
        Assert.assertEquals("<p>hello</p>", mail.get().getHtmlContent());
    }
}
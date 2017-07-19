package com.javangarda.fantacalcio.mail.application.internal.impl;

import com.javangarda.fantacalcio.mail.application.internal.storage.SentEmail;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class SimpleSentEmailFactoryTest {

    private SimpleSentEmailFactory simpleSentEmailFactory;

    @Before
    public void setUp(){
        simpleSentEmailFactory = new SimpleSentEmailFactory();
    }

    @Test
    public void should_create_entity(){
        //given:
        //when:
        SentEmail sentEmail = simpleSentEmailFactory.create("john@doe.com","<html></html>","title", LocalDateTime.now());
        //then:
        assertEquals(sentEmail.getEmail(), "john@doe.com");
        assertEquals(sentEmail.getTitle(), "title");
    }
}
package com.javangarda.fantacalcio.mail.application.gateway;

import com.javangarda.fantacalcio.mail.application.gateway.data.SentMailDTO;

import java.util.Optional;

public interface QueryFacade {
    Optional<SentMailDTO> getById(String id);
}

package com.javangarda.fantacalcio.mail.application.gateway;

import com.javangarda.fantacalcio.mail.application.gateway.data.SentEventDTO;

import java.util.Optional;

public interface QueryFacade {
    Optional<SentEventDTO> getById(String id);
}

package com.javangarda.fantacalcio.mail.application.gateway.impl;

import com.javangarda.fantacalcio.mail.application.gateway.data.SentEventDTO;
import com.javangarda.fantacalcio.mail.application.gateway.QueryFacade;
import com.javangarda.fantacalcio.mail.application.internal.storage.SentEmail;
import com.javangarda.fantacalcio.mail.application.internal.storage.SentEmailRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class SimpleQueryFacade implements QueryFacade {

    private final SentEmailRepository sentEmailRepository;

    @Override
    public Optional<SentEventDTO> getById(String id) {
        return sentEmailRepository.findById(id).map(this::map);
    }

    private SentEventDTO map(SentEmail entity) {
        SentEventDTO dto = new SentEventDTO();
        dto.setId(entity.getId());
        dto.setHtmlContent(entity.getHtmlContent());
        dto.setRecipientEmail(entity.getEmail());
        dto.setSentDateTime(entity.getSentDateTime());
        return dto;
    }
}

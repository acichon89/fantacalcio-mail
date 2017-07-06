package com.javangarda.fantacalcio.mail.application.gateway.impl;

import com.javangarda.fantacalcio.mail.application.gateway.data.SentMailDTO;
import com.javangarda.fantacalcio.mail.application.gateway.QueryFacade;
import com.javangarda.fantacalcio.mail.application.internal.storage.SentEmail;
import com.javangarda.fantacalcio.mail.application.internal.storage.SentEmailRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class SimpleQueryFacade implements QueryFacade {

    private final SentEmailRepository sentEmailRepository;

    @Override
    public Optional<SentMailDTO> getById(String id) {
        return sentEmailRepository.findById(id).map(this::map);
    }

    private SentMailDTO map(SentEmail entity) {
        SentMailDTO dto = new SentMailDTO();
        dto.setId(entity.getId());
        dto.setHtmlContent(entity.getHtmlContent());
        dto.setRecipientEmail(entity.getEmail());
        dto.setSentDateTime(entity.getSentDateTime());
        return dto;
    }
}

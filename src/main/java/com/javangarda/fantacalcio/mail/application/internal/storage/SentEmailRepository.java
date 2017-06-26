package com.javangarda.fantacalcio.mail.application.internal.storage;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface SentEmailRepository extends Repository<SentEmail, String> {
    SentEmail save(SentEmail entity);
    Optional<SentEmail> findById(String id);
}

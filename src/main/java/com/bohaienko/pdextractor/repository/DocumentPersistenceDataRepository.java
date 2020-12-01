package com.bohaienko.pdextractor.repository;

import com.bohaienko.pdextractor.model.common.DocumentPersistenceData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentPersistenceDataRepository extends JpaRepository<DocumentPersistenceData, Long> {
}

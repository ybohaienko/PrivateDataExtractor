package com.bohaienko.pdextractor.repository;

import com.bohaienko.pdextractor.model.DocumentPersistenceData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentPersistenceDataRepository extends JpaRepository<DocumentPersistenceData, Long> {
	List<DocumentPersistenceData> findByDocumentNameAndDocumentPath(String documentName, String documentPath);

	Optional<DocumentPersistenceData> findById(Long id);
}

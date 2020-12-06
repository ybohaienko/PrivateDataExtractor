package com.bohaienko.pdextractor.repository;

import com.bohaienko.pdextractor.model.SourceDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SourceDocumentRepository extends JpaRepository<SourceDocument, Long> {
	List<SourceDocument> findByDocumentNameAndDocumentPath(String documentName, String documentPath);

	Optional<SourceDocument> findById(Long id);
}

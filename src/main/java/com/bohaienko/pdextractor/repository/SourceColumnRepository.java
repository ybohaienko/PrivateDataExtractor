package com.bohaienko.pdextractor.repository;

import com.bohaienko.pdextractor.model.SourceColumn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SourceColumnRepository extends JpaRepository<SourceColumn, Long> {
	List<SourceColumn> findByDocumentId(Long id);
}

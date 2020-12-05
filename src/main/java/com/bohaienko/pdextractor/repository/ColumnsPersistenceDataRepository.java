package com.bohaienko.pdextractor.repository;

import com.bohaienko.pdextractor.model.common.ColumnsPersistenceData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColumnsPersistenceDataRepository extends JpaRepository<ColumnsPersistenceData, Long> {
	List<ColumnsPersistenceData> findByDocumentId(Long id);
}

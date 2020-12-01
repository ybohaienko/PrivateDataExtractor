package com.bohaienko.pdextractor.repository;

import com.bohaienko.pdextractor.model.common.ColumnPersistenceData;
import com.bohaienko.pdextractor.model.common.DocumentPersistenceData;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Column;

public interface ColumnPersistenceDataRepository extends JpaRepository<ColumnPersistenceData, Long> {
}

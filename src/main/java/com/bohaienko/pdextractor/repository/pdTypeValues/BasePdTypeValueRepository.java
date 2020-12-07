package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.BasePdTypeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BasePdTypeValueRepository<T, U> extends JpaRepository<T, U> {
	@SuppressWarnings("EmptyMethod")
	List<? extends BasePdTypeValue> findByValue(String value);
	List<? extends BasePdTypeValue> findByIndividualId(Long indId);
}

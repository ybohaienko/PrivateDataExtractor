package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.FirstName;

import java.util.List;

public interface FirstNameRepository extends BasePdTypeValueRepository<FirstName, Long> {
	@Override
	List<FirstName> findByValue(String value);
}

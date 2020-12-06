package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.FathersName;

import java.util.List;

public interface FathersNameRepository extends BasePdTypeValueRepository<FathersName, Long> {
	@Override
	List<FathersName> findByValue(String value);
}

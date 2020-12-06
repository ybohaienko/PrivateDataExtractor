package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.SecondName;

import java.util.List;

public interface SecondNameRepository extends BasePdTypeValueRepository<SecondName, Long> {
	@Override
	List<SecondName> findByValue(String value);

}

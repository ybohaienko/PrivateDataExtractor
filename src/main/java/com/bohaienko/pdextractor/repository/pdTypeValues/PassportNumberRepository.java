package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.PassportNumber;

import java.util.List;

public interface PassportNumberRepository extends BasePdTypeValueRepository<PassportNumber, Long> {
	@Override
	List<PassportNumber> findByValue(String value);
}

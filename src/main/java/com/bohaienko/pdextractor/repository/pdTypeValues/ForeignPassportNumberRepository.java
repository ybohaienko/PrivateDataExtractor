package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.ForeignPassportNumber;

import java.util.List;

public interface ForeignPassportNumberRepository extends BasePdTypeValueRepository<ForeignPassportNumber, Long> {
	@Override
	List<ForeignPassportNumber> findByValue(String value);
}

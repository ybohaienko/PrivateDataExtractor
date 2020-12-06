package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.IdTaxNumber;

import java.util.List;

public interface IdTaxNumberRepository extends CommonPdRepository<IdTaxNumber, Long> {
	@Override
	List<IdTaxNumber> findByValue(String value);
}

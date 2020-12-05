package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.IdTaxNumber;

import java.util.List;

public interface IdTaxNumberRepository extends CommonPdRepository<IdTaxNumber, Long> {
	@Override
	List<IdTaxNumber> findByValue(String value);
}

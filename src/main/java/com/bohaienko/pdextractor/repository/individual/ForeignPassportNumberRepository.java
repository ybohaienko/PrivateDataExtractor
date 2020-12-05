package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.ForeignPassportNumber;

import java.util.List;

public interface ForeignPassportNumberRepository extends CommonPdRepository<ForeignPassportNumber, Long> {
	@Override
	List<ForeignPassportNumber> findByValue(String value);
}

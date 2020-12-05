package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.PassportNumber;

import java.util.List;

public interface PassportNumberRepository extends CommonPdRepository<PassportNumber, Long> {
	@Override
	List<PassportNumber> findByValue(String value);
}

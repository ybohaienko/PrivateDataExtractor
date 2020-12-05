package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.PhoneNumber;

import java.util.List;

public interface PhoneNumberRepository extends CommonPdRepository<PhoneNumber, Long> {
	@Override
	List<PhoneNumber> findByValue(String value);
}

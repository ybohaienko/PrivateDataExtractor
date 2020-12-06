package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.PhoneNumber;

import java.util.List;

public interface PhoneNumberRepository extends CommonPdRepository<PhoneNumber, Long> {
	@Override
	List<PhoneNumber> findByValue(String value);
}

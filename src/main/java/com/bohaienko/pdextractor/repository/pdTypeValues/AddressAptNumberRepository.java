package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.AddressAptNumber;

import java.util.List;

public interface AddressAptNumberRepository extends CommonPdRepository<AddressAptNumber, Long> {
	@Override
	List<AddressAptNumber> findByValue(String value);
}

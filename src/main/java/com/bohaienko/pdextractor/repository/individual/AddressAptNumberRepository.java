package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.AddressAptNumber;

import java.util.List;

public interface AddressAptNumberRepository extends CommonPdRepository<AddressAptNumber, Long> {
	@Override
	List<AddressAptNumber> findByValue(String value);
}

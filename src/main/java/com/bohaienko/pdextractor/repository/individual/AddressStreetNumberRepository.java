package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.AddressStreetNumber;

import java.util.List;

public interface AddressStreetNumberRepository extends CommonPdRepository<AddressStreetNumber, Long> {
	@Override
	List<AddressStreetNumber> findByValue(String value);
}

package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.AddressStreetNumber;

import java.util.List;

public interface AddressStreetNumberRepository extends BasePdTypeValueRepository<AddressStreetNumber, Long> {
	@Override
	List<AddressStreetNumber> findByValue(String value);
}

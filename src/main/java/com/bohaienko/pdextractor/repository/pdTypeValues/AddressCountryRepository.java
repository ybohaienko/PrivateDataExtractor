package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.AddressCountry;

import java.util.List;

public interface AddressCountryRepository extends BasePdTypeValueRepository<AddressCountry, Long> {
	@Override
	List<AddressCountry> findByValue(String value);

}

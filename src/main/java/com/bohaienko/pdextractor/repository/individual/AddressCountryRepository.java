package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.AddressCountry;

import java.util.List;

public interface AddressCountryRepository extends CommonPdRepository<AddressCountry, Long> {
	@Override
	List<AddressCountry> findByValue(String value);

}

package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.AddressCity;

import java.util.List;

public interface AddressCityRepository extends CommonPdRepository<AddressCity, Long> {
	@Override
	List<AddressCity> findByValue(String value);
}

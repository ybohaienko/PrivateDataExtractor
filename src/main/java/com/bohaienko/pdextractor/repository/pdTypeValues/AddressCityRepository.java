package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.AddressCity;

import java.util.List;

public interface AddressCityRepository extends BasePdTypeValueRepository<AddressCity, Long> {
	@Override
	List<AddressCity> findByValue(String value);
}

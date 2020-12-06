package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.WorkingPlace;

import java.util.List;

public interface WorkingPlaceRepository extends BasePdTypeValueRepository<WorkingPlace, Long> {
	@Override
	List<WorkingPlace> findByValue(String value);
}

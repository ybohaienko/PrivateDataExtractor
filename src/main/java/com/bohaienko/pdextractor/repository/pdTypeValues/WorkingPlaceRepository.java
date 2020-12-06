package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.WorkingPlace;

import java.util.List;

public interface WorkingPlaceRepository extends CommonPdRepository<WorkingPlace, Long> {
	@Override
	List<WorkingPlace> findByValue(String value);
}

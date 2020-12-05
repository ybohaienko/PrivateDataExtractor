package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.WorkingPlace;

import java.util.List;

public interface WorkingPlaceRepository extends CommonPdRepository<WorkingPlace, Long> {
	@Override
	List<WorkingPlace> findByValue(String value);
}

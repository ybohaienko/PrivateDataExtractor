package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.FathersName;

import java.util.List;

public interface FathersNameRepository extends CommonPdRepository<FathersName, Long> {
	@Override
	List<FathersName> findByValue(String value);
}

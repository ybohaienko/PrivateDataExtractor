package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.FirstName;

import java.util.List;

public interface FirstNameRepository extends CommonPdRepository<FirstName, Long> {
	@Override
	List<FirstName> findByValue(String value);
}

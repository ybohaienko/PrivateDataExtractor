package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.SecondName;

import java.util.List;

public interface SecondNameRepository extends CommonPdRepository<SecondName, Long> {
	@Override
	List<SecondName> findByValue(String value);

}

package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.SecondName;

import java.util.List;

public interface SecondNameRepository extends CommonPdRepository<SecondName, Long> {
	@Override
	List<SecondName> findByValue(String value);

}

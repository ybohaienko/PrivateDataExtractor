package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.Profession;

import java.util.List;

public interface ProfessionRepository extends CommonPdRepository<Profession, Long> {
	@Override
	List<Profession> findByValue(String value);

}

package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.Profession;

import java.util.List;

public interface ProfessionRepository extends BasePdTypeValueRepository<Profession, Long> {
	@Override
	List<Profession> findByValue(String value);

}

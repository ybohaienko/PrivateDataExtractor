package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.Website;

import java.util.List;

public interface WebsiteRepository extends BasePdTypeValueRepository<Website, Long> {
	@Override
	List<Website> findByValue(String value);
}

package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.Website;

import java.util.List;

public interface WebsiteRepository extends CommonPdRepository<Website, Long> {
	@Override
	List<Website> findByValue(String value);
}

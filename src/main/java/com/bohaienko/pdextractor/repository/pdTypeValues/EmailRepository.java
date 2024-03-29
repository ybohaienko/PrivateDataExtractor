package com.bohaienko.pdextractor.repository.pdTypeValues;

import com.bohaienko.pdextractor.model.pdTypeValues.Email;

import java.util.List;

public interface EmailRepository extends BasePdTypeValueRepository<Email, Long> {
	@Override
	List<Email> findByValue(String value);
}

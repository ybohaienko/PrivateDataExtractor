package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.Email;

import java.util.List;

public interface EmailRepository extends CommonPdRepository<Email, Long> {
	@Override
	List<Email> findByValue(String value);
}

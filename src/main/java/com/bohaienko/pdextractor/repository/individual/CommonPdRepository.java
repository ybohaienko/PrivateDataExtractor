package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.CommonPd;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CommonPdRepository<T, U> extends JpaRepository<T, U> {
	List<? extends CommonPd> findByValue(String value);
}

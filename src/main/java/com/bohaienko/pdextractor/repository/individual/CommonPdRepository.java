package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.CommonPd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CommonPdRepository<T, U> extends JpaRepository<T, U> {
	@SuppressWarnings("EmptyMethod")
	List<? extends CommonPd> findByValue(String value);
}

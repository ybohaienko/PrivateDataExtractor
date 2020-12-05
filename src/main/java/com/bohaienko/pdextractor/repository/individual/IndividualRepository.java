package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.Individual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndividualRepository extends JpaRepository<Individual, Long> {
	Optional<Individual> findById(Long id);
}

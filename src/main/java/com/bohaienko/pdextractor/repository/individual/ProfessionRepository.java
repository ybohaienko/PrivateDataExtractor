package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionRepository extends JpaRepository<Profession, Long> {
}

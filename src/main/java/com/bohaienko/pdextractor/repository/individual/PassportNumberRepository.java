package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.PassportNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportNumberRepository extends JpaRepository<PassportNumber, Long> {
}

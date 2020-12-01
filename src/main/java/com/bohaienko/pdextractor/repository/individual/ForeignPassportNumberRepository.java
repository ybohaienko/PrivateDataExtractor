package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.ForeignPassportNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForeignPassportNumberRepository extends JpaRepository<ForeignPassportNumber, Long> {
}

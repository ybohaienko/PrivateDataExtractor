package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
}

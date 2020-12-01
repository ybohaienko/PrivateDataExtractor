package com.bohaienko.pdextractor.repository.individual;

import com.bohaienko.pdextractor.model.individual.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}

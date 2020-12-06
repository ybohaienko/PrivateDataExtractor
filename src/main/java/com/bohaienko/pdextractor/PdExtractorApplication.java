package com.bohaienko.pdextractor;

import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.model.SourceDocument;
import com.bohaienko.pdextractor.model.pdTypeValues.FirstName;
import com.bohaienko.pdextractor.repository.IndividualRepository;
import com.bohaienko.pdextractor.repository.SourceDocumentRepository;
import com.bohaienko.pdextractor.repository.pdTypeValues.FirstNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.UUID;

@SpringBootApplication
public class PdExtractorApplication {

//	@Autowired
//	FirstNameRepository firstNameRepository;
//	@Autowired
//	SourceDocumentRepository sourceDocumentRepository;
//	@Autowired
//	IndividualRepository individualRepository;

	public static void main(String[] args) {
		SpringApplication.run(PdExtractorApplication.class, args);
	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void some() {
//		SourceDocument document = sourceDocumentRepository.save(new SourceDocument("a", "a"));
//		Individual individual = individualRepository.save(new Individual(UUID.randomUUID()));
//		System.out.println(document);
//		System.out.println(individual);
//	}
}

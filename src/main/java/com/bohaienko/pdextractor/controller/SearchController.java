package com.bohaienko.pdextractor.controller;

import com.bohaienko.pdextractor.config.RepoInitializer;
import com.bohaienko.pdextractor.model.pdTypeValues.BasePdTypeValue;
import com.bohaienko.pdextractor.repository.IndividualRepository;
import com.bohaienko.pdextractor.repository.pdTypeValues.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.bohaienko.pdextractor.model.message.ResponseMessagesTexts.NOT_FOUND;
import static com.bohaienko.pdextractor.utils.Commons.intersect;
import static com.bohaienko.pdextractor.utils.Commons.removeDuplicates;

@RestController
@RequestMapping("/search-by")
class SearchController {

	@Autowired
	private IndividualRepository individualRepository;

	@Autowired
	private PassportNumberRepository passportNumberRepository;

	@Autowired
	private IdTaxNumberRepository idTaxNumberRepository;

	@Autowired
	private FirstNameRepository firstNameRepository;

	@Autowired
	private SecondNameRepository secondNameRepository;

	@Autowired
	private FathersNameRepository fathersNameRepository;

	@Autowired
	private PhoneNumberRepository phoneNumberRepository;

	@Autowired
	private ForeignPassportNumberRepository foreignPassportNumberRepository;

	@Autowired
	private RepoInitializer repoInitializer;

	@GetMapping("/passport")
	List<BasePdTypeValue> findByPassportNumber(String value) {
		try {
			Long individualId = Objects.requireNonNull(passportNumberRepository.findByValue(value)
					.stream().findFirst()
					.orElse(null))
					.getIndividual().getId();
			return findAllIndividualAttributes(individualId);
		} catch (NullPointerException npe) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND, npe);
		}
	}

	@GetMapping("/id-tax")
	List<BasePdTypeValue> findByIdTaxNumber(String value) {
		try {
			Long individualId = Objects.requireNonNull(idTaxNumberRepository.findByValue(value)
					.stream().findFirst()
					.orElse(null))
					.getIndividual().getId();
			return findAllIndividualAttributes(individualId);
		} catch (NullPointerException npe) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND, npe);
		}
	}

	@GetMapping("/unique-phone")
	List<BasePdTypeValue> finByUniqueAndPhoneNumber(String firstNameValue,
													String secondNameValue,
													String fathersNameValue,
													String phoneNumberValue) {
		try {
			List<Long> fnIndividualId = Objects.requireNonNull(firstNameRepository.findByValue(firstNameValue)).stream()
					.map(e -> e.getIndividual().getId())
					.collect(Collectors.toList());
			List<Long> snIndividualId = Objects.requireNonNull(secondNameRepository.findByValue(secondNameValue)).stream()
					.map(e -> e.getIndividual().getId())
					.collect(Collectors.toList());
			List<Long> fanIndividualId = Objects.requireNonNull(fathersNameRepository.findByValue(fathersNameValue)).stream()
					.map(e -> e.getIndividual().getId())
					.collect(Collectors.toList());
			List<Long> pnIndividualId = Objects.requireNonNull(phoneNumberRepository.findByValue(phoneNumberValue)).stream()
					.map(e -> e.getIndividual().getId())
					.collect(Collectors.toList());
			Long individualId = removeDuplicates(intersect(fnIndividualId, snIndividualId, fanIndividualId, pnIndividualId)).get(0);
			return findAllIndividualAttributes(individualId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND, e);
		}
	}

	@GetMapping("/unique-fpassport")
	List<BasePdTypeValue> finByUniqueAndForeignPassportNumber(String firstNameValue,
															  String secondNameValue,
															  String fathersNameValue,
															  String foreignPassportNumberValue) {
		try {
			List<Long> fnIndividualId = Objects.requireNonNull(firstNameRepository.findByValue(firstNameValue)).stream()
					.map(e -> e.getIndividual().getId())
					.collect(Collectors.toList());
			List<Long> snIndividualId = Objects.requireNonNull(secondNameRepository.findByValue(secondNameValue)).stream()
					.map(e -> e.getIndividual().getId())
					.collect(Collectors.toList());
			List<Long> fanIndividualId = Objects.requireNonNull(fathersNameRepository.findByValue(fathersNameValue)).stream()
					.map(e -> e.getIndividual().getId())
					.collect(Collectors.toList());
			List<Long> fpIndividualId = Objects.requireNonNull(foreignPassportNumberRepository.findByValue(foreignPassportNumberValue)).stream()
					.map(e -> e.getIndividual().getId())
					.collect(Collectors.toList());
			Long individualId = removeDuplicates(intersect(fnIndividualId, snIndividualId, fanIndividualId, fpIndividualId)).get(0);
			return findAllIndividualAttributes(individualId);
		} catch (IndexOutOfBoundsException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND, e);
		}
	}

	private List<BasePdTypeValue> findAllIndividualAttributes(Long IndividualId) {
		List<BasePdTypeValue> result = new ArrayList<>();
		try {
			repoInitializer.getRepositories()
					.values()
					.forEach(repo -> {
						//noinspection unchecked
						result.addAll(
								repo.findByIndividualId(IndividualId));
					});
		} catch (Exception ignored) {
		}
		return result;
	}
}

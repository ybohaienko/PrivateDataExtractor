package com.bohaienko.pdextractor.controller;

import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.model.pdTypeValues.PassportNumber;
import com.bohaienko.pdextractor.repository.IndividualRepository;
import com.bohaienko.pdextractor.repository.pdTypeValues.PassportNumberRepository;
import com.bohaienko.pdextractor.utils.IndividualNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/individual")
class IndividualController {

	@Autowired
	private IndividualRepository individualRepository;

	@Autowired
	private PassportNumberRepository passportNumberRepository;

	@GetMapping("/findByPassportNumber")
	PassportNumber findByPassportNumber(String passportNumber) {
		passportNumberRepository.findByValue(passportNumber);
		return passportNumberRepository.findByValue(passportNumber).stream().findFirst().orElse(null);
	}


	@GetMapping("/individuals/{id}")
	Individual show(@PathVariable Long id) {

		return individualRepository.findById(id)
				.orElseThrow(() -> new IndividualNotFoundException(id));
	}

	@PostMapping("/individuals")
	Individual create(@RequestBody Individual newIndividual) {
		return individualRepository.save(newIndividual);
	}

	@PutMapping("/individuals/{id}")
	Individual replace(@RequestBody Individual newIndividual, @PathVariable Long id) {

		return individualRepository.findById(id)
				.map(individualRepository::save)
				.orElseGet(() -> {
					newIndividual.setId(id);
					return individualRepository.save(newIndividual);
				});
	}

	@DeleteMapping("/individuals/{id}")
	void delete(@PathVariable Long id) {
		individualRepository.deleteById(id);
	}
}

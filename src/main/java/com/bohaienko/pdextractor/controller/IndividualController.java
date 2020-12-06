package com.bohaienko.pdextractor.controller;

import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.repository.IndividualRepository;
import com.bohaienko.pdextractor.utils.IndividualNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class IndividualController {

	private final IndividualRepository repository;

	IndividualController(IndividualRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/individuals")
	List<Individual> list() {
		return repository.findAll();
	}

	@GetMapping("/individuals/{id}")
	Individual show(@PathVariable Long id) {

		return repository.findById(id)
				.orElseThrow(() -> new IndividualNotFoundException(id));
	}

	@PostMapping("/individuals")
	Individual create(@RequestBody Individual newIndividual) {
		return repository.save(newIndividual);
	}

	@PutMapping("/individuals/{id}")
	Individual replace(@RequestBody Individual newIndividual, @PathVariable Long id) {

		return repository.findById(id)
				.map(repository::save)
				.orElseGet(() -> {
					newIndividual.setId(id);
					return repository.save(newIndividual);
				});
	}

	@DeleteMapping("/individuals/{id}")
	void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
}

package com.bohaienko.pdextractor.service;

import com.bohaienko.pdextractor.config.GenericInstanceCreator;
import com.bohaienko.pdextractor.config.RepoInitializer;
import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.model.PrivateDataType;
import com.bohaienko.pdextractor.model.SourceDocument;
import com.bohaienko.pdextractor.model.occasional.PrivateDataValue;
import com.bohaienko.pdextractor.model.pdTypeValues.BasePdTypeValue;
import com.bohaienko.pdextractor.repository.IndividualRepository;
import com.bohaienko.pdextractor.repository.pdTypeValues.BasePdTypeValueRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.stream.Collectors.toList;

@Log4j2
@Component
public class PDProcessor {

	@Autowired
	private IndividualRepository individualRepository;

	@Autowired
	private RepoInitializer repoInitializer;

	@Autowired
	private PDTypeProcessor pdTypeProcessor;

	@Autowired
	private GenericInstanceCreator genericInstanceCreator;

	private Map<Long, Map<PrivateDataType, Integer>> occurrences;
	private Map<PrivateDataType, BasePdTypeValueRepository> repos;

	void process(List<List<PrivateDataValue>> payload) {
		log.info("Processing exctracted payload from found file");

		repos = repoInitializer.getRepositories();

		payload.forEach(row -> {
			List<PrivateDataType> privateDataTypeList = new ArrayList<>();
			row.forEach(e -> privateDataTypeList.add(e.getType()));

			Arrays.asList(PrivateDataType.getUnique()).forEach(uniqueTypesSet -> {
				if (privateDataTypeList.containsAll(Arrays.asList(uniqueTypesSet))) {
					Long individualId = checkUniqueSetExistsForIndividual(row, uniqueTypesSet);
					if (individualId != null)
						saveAttributes(Objects.requireNonNull(individualRepository.findById(individualId).orElse(null)), row);
					else
						saveAttributes(individualRepository.save(new Individual(UUID.randomUUID())), row);
				} else
					saveAttributes(row);
			});
		});
	}

	private void saveAttributes(Individual individual, List<PrivateDataValue> privateDataValues) {
		log.debug("Saving private data values for the individual with UUID: {}", individual.getUuid());
		privateDataValues.forEach(value -> {
			SourceDocument doc = pdTypeProcessor.saveDocumentByPath(value.getFullPath());
			genericInstanceCreator.saveGeneric(value.getType(), value.getValue(), doc, individual);
		});
	}

	private void saveAttributes(List<PrivateDataValue> privateDataValues) {
		log.debug("Saving private data values without an individual");
		privateDataValues.forEach(value -> {
			SourceDocument doc = pdTypeProcessor.saveDocumentByPath(value.getFullPath());
			genericInstanceCreator.saveGeneric(value.getType(), value.getValue(), doc, null);
		});
	}

	private Long checkUniqueSetExistsForIndividual(List<PrivateDataValue> pdValues, PrivateDataType[] uniqueTypesSet) {
		AtomicReference<Long> individualId = new AtomicReference<>();
		log.debug("Checking existence of found PD values for the unique set of values: {}", Arrays.asList(uniqueTypesSet));

		occurrences = new HashMap<>();
		Arrays.asList(uniqueTypesSet).forEach(pdType -> {
			List<PrivateDataValue> filteredPdValues = pdValues.stream()
					.filter(pdValue -> pdValue.getType().equals(pdType))
					.collect(toList());
			filteredPdValues.forEach(filteredPdValue -> {
				@SuppressWarnings("unchecked")
				List<? extends BasePdTypeValue> list = repos.get(filteredPdValue.getType())
						.findByValue(filteredPdValue.getValue());
				list.forEach(e -> {
					Long foundId = e.getIndividual().getId();
					if (some(foundId, pdType, uniqueTypesSet.length))
						individualId.set(foundId);
				});
			});
		});
		return individualId.get();
	}

	private boolean some(Long foundId, PrivateDataType pdType, int uniqueTypesSetLength) {
		boolean result = false;
		Map<PrivateDataType, Integer> typesOccurrence = occurrences.get(foundId);
		if (typesOccurrence == null)
			typesOccurrence = new HashMap<>();

		Integer occurrence = typesOccurrence.get(pdType);
		if (occurrence == null) {
			typesOccurrence.put(pdType, 1);
			occurrences.put(foundId, typesOccurrence);
		} else {
			typesOccurrence.put(pdType, ++occurrence);
			occurrences.put(foundId, typesOccurrence);
		}

		if (typesOccurrence.size() == uniqueTypesSetLength)
			result = true;
		return result;
	}
}

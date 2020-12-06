package com.bohaienko.pdextractor.service;

import com.bohaienko.pdextractor.config.GenericInstanceCreator;
import com.bohaienko.pdextractor.config.RepoInitializer;
import com.bohaienko.pdextractor.model.PrivateDataType;
import com.bohaienko.pdextractor.model.DocumentPersistenceData;
import com.bohaienko.pdextractor.model.occasional.PrivateDataValue;
import com.bohaienko.pdextractor.model.pdTypeValues.CommonPd;
import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.repository.DocumentPersistenceDataRepository;
import com.bohaienko.pdextractor.repository.pdTypeValues.CommonPdRepository;
import com.bohaienko.pdextractor.repository.IndividualRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.bohaienko.pdextractor.utils.Commons.getFileNameByFullPath;
import static com.bohaienko.pdextractor.utils.Commons.getLocationByFullPath;
import static java.util.stream.Collectors.toList;

@Log4j2
@Component
public class PDProcessor {

	@Autowired
	private IndividualRepository individualRepository;

	@Autowired
	private DocumentPersistenceDataRepository docRepository;

	@Autowired
	private RepoInitializer repoInitializer;

	@Autowired
	private GenericInstanceCreator genericInstanceCreator;

	private Map<Long, Map<PrivateDataType, Integer>> occurrences;
	private Map<PrivateDataType, CommonPdRepository> repos;


	public void process(List<List<PrivateDataValue>> payload) {
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
				} else {
					saveAttributes(row);
				}
			});
		});
	}

	private void saveAttributes(Individual individual, List<PrivateDataValue> privateDataValues) {
		log.info("Saving private data values for the individual with UUID: {}", individual.getUuid());
		privateDataValues.forEach(value -> {
			DocumentPersistenceData doc = getDocument(value);
			genericInstanceCreator.saveGeneric(value.getType(), value.getValue(), doc, individual);
		});
	}

	private void saveAttributes(List<PrivateDataValue> privateDataValues) {
		log.info("Saving private data values without an individual");
		privateDataValues.forEach(value -> {
			DocumentPersistenceData doc = getDocument(value);
			genericInstanceCreator.saveGeneric(value.getType(), value.getValue(), doc, null);
		});
	}

	private DocumentPersistenceData getDocument(PrivateDataValue value) {
		String srcDocPath = value.getFullPath();
		DocumentPersistenceData doc = retrieveSavedDocumentByPath(srcDocPath);
		if (doc == null)
			return new DocumentPersistenceData(
					getFileNameByFullPath(srcDocPath),
					getLocationByFullPath(srcDocPath)
			);
		return doc;
	}


	private DocumentPersistenceData retrieveSavedDocumentByPath(String fullPath) {
		return docRepository.findByDocumentNameAndDocumentPath(
				getFileNameByFullPath(fullPath),
				getLocationByFullPath(fullPath)
		).stream().findFirst().orElse(null);
	}

	private Long checkUniqueSetExistsForIndividual(List<PrivateDataValue> pdValues, PrivateDataType[] uniqueTypesSet) {
		AtomicReference<Long> individualId = new AtomicReference<>();
		log.info("Checking existence of found PD values for the unique set of values: {}", Arrays.asList(uniqueTypesSet));

		occurrences = new HashMap<>();
		Arrays.asList(uniqueTypesSet).forEach(pdType -> {
			List<PrivateDataValue> filteredPdValues = pdValues.stream()
					.filter(pdValue -> pdValue.getType().equals(pdType))
					.collect(toList());
			filteredPdValues.forEach(filteredPdValue -> {
				@SuppressWarnings("unchecked")
				List<? extends CommonPd> list = repos.get(filteredPdValue.getType())
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

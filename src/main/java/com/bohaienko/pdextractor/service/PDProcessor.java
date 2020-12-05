package com.bohaienko.pdextractor.service;

import com.bohaienko.pdextractor.config.GenericInstanceCreator;
import com.bohaienko.pdextractor.config.RepoInitializer;
import com.bohaienko.pdextractor.model.PrivateDataType;
import com.bohaienko.pdextractor.model.common.DocumentPersistenceData;
import com.bohaienko.pdextractor.model.common.PrivateDataValue;
import com.bohaienko.pdextractor.model.individual.CommonPd;
import com.bohaienko.pdextractor.model.individual.Individual;
import com.bohaienko.pdextractor.repository.ColumnsPersistenceDataRepository;
import com.bohaienko.pdextractor.repository.DocumentPersistenceDataRepository;
import com.bohaienko.pdextractor.repository.individual.CommonPdRepository;
import com.bohaienko.pdextractor.repository.individual.IndividualRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.bohaienko.pdextractor.utils.Commons.getFileNameByLocation;
import static com.bohaienko.pdextractor.utils.Commons.getPathByFullLocation;
import static java.util.stream.Collectors.toList;

@Log4j2
@Component
public class PDProcessor {

	@Autowired
	private IndividualRepository individualRepository;

	@Autowired
	private DocumentPersistenceDataRepository docRepository;

	@Autowired
	private ColumnsPersistenceDataRepository columnRepository;

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
			Individual newIndividual = new Individual(UUID.randomUUID());

			Arrays.asList(PrivateDataType.getUnique()).forEach(uniqueTypesSet -> {
				if (privateDataTypeList.containsAll(Arrays.asList(uniqueTypesSet))) {
					Long individualId = checkUniqueSetExistsForIndividual(row, uniqueTypesSet);
					if (individualId != null)
						saveIndividualAttributes(individualRepository.findById(individualId).orElse(null), row);
					else
						saveIndividualAttributes(newIndividual, row);
				} else {
					saveIndividualAttributes(newIndividual, row);
				}
			});
		});
	}

	private void saveIndividualAttributes(Individual individual, List<PrivateDataValue> privateDataValues) {
		log.info("Saving private data values for the individual with UUID: {}", individual.getUuid());
		privateDataValues.forEach(value -> {
			String srcDocPath = value.getFullPath();
			DocumentPersistenceData doc = retrieveSavedDocumentByPath(srcDocPath);
			if (doc == null)
				doc = new DocumentPersistenceData(getFileNameByLocation(srcDocPath), getPathByFullLocation(srcDocPath));
			genericInstanceCreator.saveGeneric(value.getType(), value.getValue(), doc, individual);
		});
	}

	private DocumentPersistenceData retrieveSavedDocumentByPath(String fullPath) {
		return docRepository.findByDocumentNameAndDocumentPath(
				getFileNameByLocation(fullPath),
				getPathByFullLocation(fullPath)
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


	public List<List<PrivateDataValue>> createDummy() {
		List<PrivateDataValue> dummy = new ArrayList<>();
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_FIRST_NAME, "Ivan", "/root/some.file"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_SECOND_NAME, "Rudenko", "/root/some.file"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_FATHERS_NAME, "Ivanovych", "/root/some.file"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_ID_TAX_NUMBER, "123456789", "/root/some.file"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_PASSPORT_NUMBER, "MR123456", "/root/some.file"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_EMAIL, "some@some.so", "/root/some.file"));
		List<PrivateDataValue> dummy2 = new ArrayList<>();
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_FIRST_NAME, "Petro", "/root/some2.file"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_SECOND_NAME, "Melnyk", "/root/some2.file"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_FATHERS_NAME, "Petrovych", "/root/some2.file"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_ID_TAX_NUMBER, "987654321", "/root/some2.file"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_PASSPORT_NUMBER, "ML123456", "/root/some2.file"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_PHONE_NUMBER, "+380501234567", "/root/some2.file"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_FATHERS_NAME, "Petrovych", "/root/some2.file"));
		List<List<PrivateDataValue>> dummy3 = new ArrayList<>();
		dummy3.add(dummy);
		dummy3.add(dummy2);
		return dummy3;
	}

}

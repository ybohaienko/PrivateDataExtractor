package com.bohaienko.pdextractor.service;

import com.bohaienko.pdextractor.model.PrivateDataType;
import com.bohaienko.pdextractor.model.common.ColumnsPersistenceData;
import com.bohaienko.pdextractor.model.common.DocumentPersistenceData;
import com.bohaienko.pdextractor.model.common.PrivateDataValue;
import com.bohaienko.pdextractor.repository.ColumnsPersistenceDataRepository;
import com.bohaienko.pdextractor.repository.DocumentPersistenceDataRepository;
import com.bohaienko.pdextractor.service.parser.CommonParser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@Component
public class FileProcessor {

	@Autowired
	CommonParser commonParser;

	@Autowired
	DocumentPersistenceDataRepository docRepository;

	@Autowired
	ColumnsPersistenceDataRepository colRepository;

	public List<List<PrivateDataValue>> retrievePayloadFromFileByDocument(String tempLocalFilePath, Long docId) {
		List<List<PrivateDataValue>> payload = new ArrayList<>();
		try {
			DocumentPersistenceData document = docRepository.findById(docId).orElse(null);
			List<ColumnsPersistenceData> columns = colRepository.findByDocumentId(docId);
			List<Map<String, String>> data = commonParser.getRawData(
					tempLocalFilePath,
					0
			);

			AtomicInteger counter = new AtomicInteger();
			data.forEach(row -> {
				log.info("Processing {}th row of the document: {}", counter.getAndIncrement(), document.getDocumentName());

				List<PrivateDataValue> parsedRow = new ArrayList<>();
				row.forEach((key, value) -> {
					ColumnsPersistenceData targetColumn = columns.stream()
							.filter(column -> column.getColumnHeader().equals(key))
							.findFirst().orElse(null);
					parsedRow.add(new PrivateDataValue(
							PrivateDataType.valueOf(targetColumn.getColumnRecognizedPiiType()),
							value,
							Objects.requireNonNull(document).getDocumentPath())
					);
				});
				payload.add(parsedRow);
			});
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return payload;
	}
}

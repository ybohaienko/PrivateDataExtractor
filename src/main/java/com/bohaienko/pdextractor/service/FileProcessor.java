package com.bohaienko.pdextractor.service;

import com.bohaienko.pdextractor.model.PrivateDataType;
import com.bohaienko.pdextractor.model.SourceColumn;
import com.bohaienko.pdextractor.model.SourceDocument;
import com.bohaienko.pdextractor.model.occasional.PrivateDataValue;
import com.bohaienko.pdextractor.repository.SourceColumnRepository;
import com.bohaienko.pdextractor.repository.SourceDocumentRepository;
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
	SourceDocumentRepository docRepository;

	@Autowired
	SourceColumnRepository colRepository;

	public List<List<PrivateDataValue>> retrievePayloadFromFileByDocument(String tempLocalFilePath, Long docId) {
		List<List<PrivateDataValue>> payload = new ArrayList<>();
		try {
			SourceDocument sourceDocument = Objects.requireNonNull(docRepository.findById(docId).orElse(null));
			List<SourceColumn> columns = colRepository.findByDocumentId(docId);
			List<Map<String, String>> data = commonParser.getRawData(
					tempLocalFilePath,
					0
			);

			AtomicInteger counter = new AtomicInteger();
			data.forEach(row -> {
				log.info("Processing {}th row of the document: {}", counter.getAndIncrement(), sourceDocument.getDocumentName());

				List<PrivateDataValue> parsedRow = new ArrayList<>();
				row.forEach((key, value) -> {
					SourceColumn targetColumn = columns.stream()
							.filter(column -> column.getColumnHeader().equals(key))
							.findFirst().orElse(null);
					parsedRow.add(new PrivateDataValue(
							PrivateDataType.valueOf(Objects.requireNonNull(targetColumn).getColumnRecognizedPiiType()),
							value,
							sourceDocument.getDocumentPath() + sourceDocument.getDocumentName())
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

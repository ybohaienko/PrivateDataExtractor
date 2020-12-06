package com.bohaienko.pdextractor.service;

import com.bohaienko.pdextractor.model.PrivateDataType;
import com.bohaienko.pdextractor.model.SourceColumn;
import com.bohaienko.pdextractor.model.occasional.DocumentData;
import com.bohaienko.pdextractor.model.SourceDocument;
import com.bohaienko.pdextractor.repository.SourceColumnRepository;
import com.bohaienko.pdextractor.repository.SourceDocumentRepository;
import com.bohaienko.pdextractor.service.parser.CommonParser;
import com.bohaienko.pdextractor.service.parser.CsvParserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.bohaienko.pdextractor.utils.Commons.getFileNameByFullPath;
import static com.bohaienko.pdextractor.utils.Commons.getLocationByFullPath;

@Log4j2
@Component
public class PDTypeProcessor {
	@Autowired
	CommonParser commonParser;

	@Autowired
	SourceDocumentRepository docRepository;

	@Autowired
	SourceColumnRepository colRepository;

	public Long processColumnsType(String tempFilePath, int lines, String srcFullPath) {
		DocumentData data = commonParser.getDocumentData(tempFilePath, lines, srcFullPath);
		DocumentData dataWithColumnTypes = getColumnSpecsByHeaders(data);
		DocumentData dataWithColumnTypesByValue = getColumnSpecByColumnValues(dataWithColumnTypes);

		dataWithColumnTypesByValue.getColumnData().forEach(columnData -> {
			log.info("Processing a type of the column with the header: {}", columnData.getHeader());

			Map<PrivateDataType, Integer> scoreSet = columnData.getColumnTypeScoresByColumnValues();
			int dictRecognizedHighestValue = columnData.getColumnTypeScoresByColumnValues() != null
					? Collections.max(scoreSet.values())
					: 0;
			if (dictRecognizedHighestValue > 0
					&& columnData.getColumnValues().size() / dictRecognizedHighestValue < 2
					&& columnData.getExpectedColumnType() != PrivateDataType.TYPE_NONE) {
				PrivateDataType type = scoreSet.entrySet().stream()
						.filter(x -> x.getValue().equals(dictRecognizedHighestValue))
						.findFirst()
						.orElseThrow(IllegalArgumentException::new)
						.getKey();
				columnData.setExpectedColumnType(type);
			}
		});

		SourceDocument doc = docRepository.save(
				new SourceDocument(
						getFileNameByFullPath(srcFullPath),
						getLocationByFullPath(srcFullPath))
		);
		dataWithColumnTypesByValue.getColumnData().forEach(c -> colRepository.save(
				new SourceColumn(
						c.getPositionNumber(),
						c.getHeader(),
						c.getExpectedColumnType().name(),
						doc
				)));
		return doc.getId();
	}

	private DocumentData getColumnSpecByColumnValues(DocumentData data) {
		data.getColumnData().forEach(column -> {
			Map<PrivateDataType, Integer> scoreSet;
			scoreSet = checkRegex(column.getColumnValues());
			if (scoreSet == null) {
				scoreSet = checkDictionaries(column.getColumnValues());
			}
			if (scoreSet == null) {
				column.setExpectedColumnType(PrivateDataType.TYPE_NONE);
			}
			column.setColumnTypeScoresByColumnValues(scoreSet);
		});
		return data;
	}

	private Map<PrivateDataType, Integer> checkDictionaries(List<String> columnValues) {
		Map<String, List<String>> dict = new CsvParserService().getValuesInColumnsByFilePath("src/main/resources/recognition/dict.csv");
		StringsComparator comparator = new StringsComparator();
		Map<PrivateDataType, Integer> scoreSet = supplyScoreSet();
		AtomicBoolean isMatch = new AtomicBoolean(false);
		columnValues.forEach(value -> dict.forEach((privateDataType, dictValues) -> {
			PrivateDataType type = PrivateDataType.valueOf(privateDataType);
			dictValues.forEach(dictValue -> {
				if (comparator.calculateResemblanseScore(value, dictValue) < 1) {
					Integer scoreValue = scoreSet.get(type);
					scoreSet.put(type, scoreValue + 1);
					isMatch.set(true);
				}
			});
		}));
		if (isMatch.get())
			return scoreSet;
		else
			return null;
	}

	private Map<PrivateDataType, Integer> checkRegex(List<String> columnValues) {
		Map<String, List<String>> regexData = new CsvParserService()
				.getValuesInColumnsByFilePath("src/main/resources/recognition/regex.csv");
		Map<PrivateDataType, Integer> scoreSet = supplyScoreSet();
		AtomicBoolean isMatch = new AtomicBoolean(false);
		columnValues.forEach(value -> regexData.forEach((privateDataType, regex) -> {
			PrivateDataType type = PrivateDataType.valueOf(privateDataType);
			regex.forEach(e -> {
				if (value.matches(e)) {
					scoreSet.put(type, scoreSet.get(type) + 1);
					isMatch.set(true);
				}
			});
		}));
		if (isMatch.get())
			return scoreSet;
		else
			return null;
	}

	private DocumentData getColumnSpecsByHeaders(DocumentData documentData) {
		documentData.getColumnData().forEach(column -> {
			Map<PrivateDataType, Integer> scoreSet = supplyScoreSet();
			StringsComparator comparator = new StringsComparator();
			scoreSet.forEach((k, v) -> scoreSet.put(k, comparator.calculateResemblanseScore(k.name(), column.getHeader())));
			PrivateDataType type = scoreSet.entrySet().stream()
					.filter(x -> x.getValue().equals(Collections.min(scoreSet.values())))
					.findFirst()
					.orElseThrow(IllegalArgumentException::new)
					.getKey();
			column.setExpectedColumnType(type);
		});
		return documentData;
	}

	private Map<PrivateDataType, Integer> supplyScoreSet() {
		Map<PrivateDataType, Integer> scoreSet = new HashMap<>();
		Arrays.asList(PrivateDataType.values()).forEach(e -> scoreSet.put(e, 0));
		return scoreSet;
	}
}

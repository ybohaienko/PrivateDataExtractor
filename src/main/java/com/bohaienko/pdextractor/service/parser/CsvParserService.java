package com.bohaienko.pdextractor.service.parser;

import com.bohaienko.pdextractor.model.Extension;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.bohaienko.pdextractor.utils.Commons.checkFileTypeForExtensions;
import static com.bohaienko.pdextractor.utils.Commons.valuesEmpty;
import static com.fasterxml.jackson.dataformat.csv.CsvParser.Feature.SKIP_EMPTY_LINES;
import static com.fasterxml.jackson.dataformat.csv.CsvParser.Feature.TRIM_SPACES;

public class CsvParserService extends CommonParser {
	private MappingIterator<Map<String, String>> it;

	List<Map<String, String>> getAllValuesByPathOfLines(String path, int lines) {
		checkFileTypeForExtensions(path, new Extension[]{Extension.CSV});
		List<Map<String, String>> data = new ArrayList<>();
		it = getCsvMappingIterator(path);

		int counter = 0;
		while (Objects.requireNonNull(it).hasNext()) {
			Map<String, String> row = it.next();
			if (!valuesEmpty(row))
				data.add(row);
			counter++;
			if (lines > 0 && counter == lines) break;
		}
		return data;
	}

	public Map<String, List<String>> getValuesInColumnsByFilePath(String path) {
		checkFileTypeForExtensions(path, new Extension[]{Extension.CSV});
		Map<String, List<String>> data = new HashMap<>();
		it = getCsvMappingIterator(path);
		while (Objects.requireNonNull(it).hasNext()) {
			Map<String, String> rowAsMap = it.next();
			rowAsMap.forEach((k, v) -> {
				if (!v.equals(""))
					if (!data.containsKey(k)) {
						List<String> values = new ArrayList<>();
						values.add(v);
						data.put(k, values);
					} else
						data.get(k).add(v);
			});
		}
		return data;
	}

	private MappingIterator<Map<String, String>> getCsvMappingIterator(String path) {
		CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
		MappingIterator<Map<String, String>> it = null;
		CsvMapper mapper = new CsvMapper();
		mapper.enable(TRIM_SPACES);
		mapper.enable(SKIP_EMPTY_LINES);
		try {
			it = mapper.readerFor(Map.class)
					.with(csvSchema)
					.readValues(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return it;
	}
}

package com.bohaienko.pdextractor.service.parser;

import com.bohaienko.pdextractor.model.Extension;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.bohaienko.pdextractor.utils.Commons.checkFileTypeForExtensions;

public class CsvParser extends CommonParser {
	List<Map<String, String>> getAllValuesByPathOfLines(String path, int lines) {
		checkFileTypeForExtensions(path, new Extension[]{Extension.CSV});

		List<Map<String, String>> data = new ArrayList<>();
		MappingIterator<Map<String, String>> it = getCsvMappingIterator(path);

		int counter = 0;
		while (Objects.requireNonNull(it).hasNext()) {
			Map<String, String> rowAsMap = it.next();
			data.add(rowAsMap);

			counter++;
			if (lines > 0 && counter == lines) break;
		}
		return data;
	}

	public Map<String, List<String>> getValuesInColumnsByFilePath(String path) {
		Map<String, List<String>> data = new HashMap<>();
		MappingIterator<Map<String, String>> it = getCsvMappingIterator(path);
		while (Objects.requireNonNull(it).hasNext()) {
			Map<String, String> rowAsMap = it.next();
			rowAsMap.forEach((k, v) -> {
				if (!data.containsKey(k) && v != null) {
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
		try {
			it = new CsvMapper().readerFor(Map.class)
					.with(csvSchema)
					.readValues(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return it;
	}

}

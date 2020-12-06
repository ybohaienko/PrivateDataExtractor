package com.bohaienko.pdextractor.service.parser;

import com.bohaienko.pdextractor.model.PrivateDataType;
import com.bohaienko.pdextractor.model.occasional.ColumnData;
import com.bohaienko.pdextractor.model.occasional.DocumentData;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.bohaienko.pdextractor.model.Extension.*;

@Log4j2
@Component
public class CommonParser {
	public DocumentData getDocumentData(String tempLocalFilePath, int lines, String srcFilePath) {
		return parseToDocumentData(
				getRawData(tempLocalFilePath, lines),
				srcFilePath
		);
	}

	public List<Map<String, String>> getRawData(String tempLocalFilePath, int lines) {
		List<Map<String, String>> rawData = null;
		String extension = FilenameUtils.getExtension(tempLocalFilePath);
		if (extension.equals(CSV.name().toLowerCase()))
			rawData = new CsvParserService().getAllValuesByPathOfLines(tempLocalFilePath, lines);
		if (extension.equals(XLS.name().toLowerCase()) || extension.equals(XLSX.name()))
			rawData = new XlsParserService().getAllValuesByPathOfLines(tempLocalFilePath, lines);
		return rawData;
	}

	private DocumentData parseToDocumentData(List<Map<String, String>> data, String sourcePath) {
		List<ColumnData> columnDataList = new ArrayList<>();
		try {
			AtomicInteger columnCounter = new AtomicInteger();
			data.get(0).forEach((header, value) -> columnDataList.add(
					new ColumnData(
							columnCounter.getAndIncrement(),
							header,
							new ArrayList<>(),
							PrivateDataType.TYPE_NONE,
							new HashMap<>()
					)
			));
			data.forEach(row -> row.forEach((header, value) -> {
				columnDataList.stream()
						.filter(e -> e.getHeader().equals(header))
						.findAny()
						.orElseThrow(IllegalArgumentException::new)
						.getColumnValues().add(value);
			}));
		} catch (Exception e) {
			e.printStackTrace();
//			log.error(e.getMessage());
		}
		return new DocumentData(columnDataList, sourcePath);
	}
}

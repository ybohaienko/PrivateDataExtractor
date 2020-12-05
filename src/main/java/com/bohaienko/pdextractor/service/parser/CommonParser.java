package com.bohaienko.pdextractor.service.parser;

import com.bohaienko.pdextractor.model.PrivateDataType;
import com.bohaienko.pdextractor.model.common.ColumnData;
import com.bohaienko.pdextractor.model.common.DocumentData;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.bohaienko.pdextractor.model.Extension.*;

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
			rawData = new CsvParser().getAllValuesByPathOfLines(tempLocalFilePath, lines);
		if (extension.equals(XLS.name().toLowerCase()) || extension.equals(XLSX.name()))
			rawData = new XlsParser().getAllValuesByPathOfLines(tempLocalFilePath, lines);
		return rawData;
	}

	private DocumentData parseToDocumentData(List<Map<String, String>> data, String sourcePath) {
		List<ColumnData> columnDataList = new ArrayList<>();
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
		data.forEach(row -> {
			row.forEach((header, value) -> {
				columnDataList.stream()
						.filter(e -> e.getHeader().equals(header))
						.findAny()
						.orElseThrow(IllegalArgumentException::new)
						.getColumnValues().add(value);
			});
		});
		return new DocumentData(columnDataList, sourcePath);
	}
}

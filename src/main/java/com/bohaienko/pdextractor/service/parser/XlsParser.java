package com.bohaienko.pdextractor.service.parser;

import com.bohaienko.pdextractor.model.Extension;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static com.bohaienko.pdextractor.utils.Commons.checkFileTypeForExtensions;

public class XlsParser extends CommonParser{
	public List<Map<String, String>> getAllValuesByPathOfLines(String path, int lines) {
		checkFileTypeForExtensions(path, new Extension[]{Extension.XLS, Extension.XLSX});
		List<Map<String, String>> data = new ArrayList<>();
		Sheet sheet = getSheet(path);
		int rows = sheet.getPhysicalNumberOfRows();

		int counter = 0;
		for (int i = 1; i < rows; i++) {
			if (!isRowEmpty(sheet.getRow(i))) {
				Map<String, String> entries = new HashMap<>();
				int columns = sheet.getRow(i).getPhysicalNumberOfCells();
				for (int j = 0; j < columns; j++) {
					entries.put(getCellValue(sheet, 0, j), getCellValue(sheet, i, j));
				}
				data.add(entries);
			}
			counter++;
			if (lines > 0 && counter == lines) break;
		}
		return data;
	}

	private Sheet getSheet(String path) {
		File file = new File(path);
		Workbook workbook = getWorkbook(path);
		return Objects.requireNonNull(workbook).getSheetAt(0);
	}

	private Workbook getWorkbook(String path) {
		Workbook workbook = null;
		String extension = FilenameUtils.getExtension(path);
		try {
			workbook = extension.equals(Extension.XLS.value())
					? new HSSFWorkbook(new FileInputStream(path))
					: new XSSFWorkbook(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}

	private String getCellValue(Sheet sheet, int row, int cell) {
		return new DataFormatter()
				.formatCellValue(sheet.getRow(row).getCell(cell))
				.trim();
	}



	private static boolean isRowEmpty(Row row) {
		if (row != null) {
			for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
				Cell cell = row.getCell(c);
				if (cell != null && cell.getCellType() != CellType.BLANK)
					return false;
			}
		}
		return true;
	}
}

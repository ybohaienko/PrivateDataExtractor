package com.bohaienko.pdextractor.service;

import com.bohaienko.pdextractor.model.PrivateDataType;
import com.bohaienko.pdextractor.model.common.PrivateDataValue;
import com.bohaienko.pdextractor.repository.ColumnPersistenceDataRepository;
import com.bohaienko.pdextractor.repository.DocumentPersistenceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PDProcessor {

	@Autowired
	DocumentPersistenceDataRepository docRepository;

	@Autowired
	ColumnPersistenceDataRepository columnRepository;

	public List<List<PrivateDataValue>> createDummy() {
		List<PrivateDataValue> dummy = new ArrayList<>();
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_FIRST_NAME, "Ivan", "p1"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_SECOND_NAME, "Rudenko", "p1"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_FATHERS_NAME, "Ivanovych", "p1"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_ID_TAX_NUMBER, "123456789", "p1"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_PASSPORT_NUMBER, "MR123456", "p1"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_EMAIL, "some@some.so", "p1"));
		List<PrivateDataValue> dummy2 = new ArrayList<>();
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_FIRST_NAME, "Petro", "p2"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_SECOND_NAME, "Melnyk", "p2"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_FATHERS_NAME, "Petrovych", "p2"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_ID_TAX_NUMBER, "987654321", "p2"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_PASSPORT_NUMBER, "ML123456", "p2"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_PHONE_NUMBER, "+380501234567", "p2"));
		dummy.add(new PrivateDataValue(PrivateDataType.TYPE_FATHERS_NAME, "Petrovych", "p2"));
		List<List<PrivateDataValue>> dummy3 = new ArrayList<>();
		dummy3.add(dummy);
		dummy3.add(dummy2);
		return dummy3;
	}

	public void process(List<List<PrivateDataValue>> payload) {
		payload.forEach(row -> {
			List<PrivateDataType> privateDataTypeList = new ArrayList<>();
			row.forEach(e -> privateDataTypeList.add(e.getType()));

			Arrays.asList(PrivateDataType.getUnique()).forEach(uniqueTypesSet -> {
				if (privateDataTypeList.containsAll(Arrays.asList(uniqueTypesSet))) {
					writeToDb(row, uniqueTypesSet);
				}
			});
		});
	}

	public void writeToDb(List<PrivateDataValue> valueList, PrivateDataType[] uniqueTypesSet) {
		if (checkExists()) {
			System.out.println(valueList);
			System.out.println("========");
			System.out.println(uniqueTypesSet);
			//merge with exists

		} else {
			//add new line
		}
	}

	public boolean checkExists() {
		return true;
	}
}

package com.bohaienko.pdextractor.model.occasional;

import com.bohaienko.pdextractor.model.PrivateDataType;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ColumnData {
	@NonNull
	Integer positionNumber;
	String header;
	List<String> columnValues;
	@NonNull
	PrivateDataType expectedColumnType;
	Map<PrivateDataType, Integer> columnTypeScoresByColumnValues;
}

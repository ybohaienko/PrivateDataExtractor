package com.bohaienko.pdextractor.model.common;

import lombok.*;

import javax.persistence.Entity;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DocumentData {
	@NonNull
	List<ColumnData> columnData;
	@NonNull
	String path;
}

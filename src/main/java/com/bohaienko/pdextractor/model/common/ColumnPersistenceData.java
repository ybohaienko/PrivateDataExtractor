package com.bohaienko.pdextractor.model.common;

import com.bohaienko.pdextractor.model.PrivateDataType;
import com.bohaienko.pdextractor.model.individual.Individual;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ColumnPersistenceData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	int columnPositionNumber;
	@NotNull
	String columnPiiType;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "document_id", nullable = false)
	private DocumentPersistenceData document;

	public ColumnPersistenceData(int columnPositionNumber, String  columnPiiType, DocumentPersistenceData document) {
		this.columnPositionNumber = columnPositionNumber;
		this.columnPiiType = columnPiiType;
		this.document = document;
	}
}

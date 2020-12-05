package com.bohaienko.pdextractor.model.common;

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
public class ColumnsPersistenceData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	int columnPositionNumber;
	@NotNull
	String columnHeader;
	@NotNull
	String columnRecognizedPiiType;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "document_id", nullable = false)
	private DocumentPersistenceData document;

	public ColumnsPersistenceData(int columnPositionNumber, String columnHeader, String columnRecognizedPiiType, DocumentPersistenceData document) {
		this.columnPositionNumber = columnPositionNumber;
		this.columnHeader = columnHeader;
		this.columnRecognizedPiiType = columnRecognizedPiiType;
		this.document = document;
	}
}

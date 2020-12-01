package com.bohaienko.pdextractor.model.common;

import com.bohaienko.pdextractor.model.PrivateDataType;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DocumentPersistenceData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	String documentName;
	@NotNull
	String documentPath;

	public DocumentPersistenceData(String documentName, String documentPath) {
		this.documentName = documentName;
		this.documentPath = documentPath;
	}
}

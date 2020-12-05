package com.bohaienko.pdextractor.model.common;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Transactional
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

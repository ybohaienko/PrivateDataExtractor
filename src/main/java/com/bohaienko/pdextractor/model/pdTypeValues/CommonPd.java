package com.bohaienko.pdextractor.model.pdTypeValues;

import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.model.DocumentPersistenceData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"value", "document_id", "individual_id"}))
@NoArgsConstructor
public class CommonPd {
	@Id
	@GeneratedValue
	private Long id;

	private String value;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "document_id", nullable = false,insertable = false,updatable = false)
	private DocumentPersistenceData documentPersistenceData;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "individual_id")
	private Individual individual;

	public CommonPd(String value, DocumentPersistenceData documentPersistenceData, Individual individual) {
		this.value = value;
		this.documentPersistenceData = documentPersistenceData;
		this.individual = individual;
	}
}

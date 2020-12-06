package com.bohaienko.pdextractor.model.pdTypeValues;

import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.model.SourceDocument;
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
public class BasePdTypeValue {
	@Id
	@GeneratedValue
	private Long id;

	private String value;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "document_id", nullable = false)
	private SourceDocument document;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "individual_id")
	private Individual individual;

	public BasePdTypeValue(String value, SourceDocument document, Individual individual) {
		this.value = value;
		this.document = document;
		this.individual = individual;
	}
}

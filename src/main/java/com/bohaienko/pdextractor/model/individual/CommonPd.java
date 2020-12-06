package com.bohaienko.pdextractor.model.individual;

import com.bohaienko.pdextractor.model.common.DocumentPersistenceData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
public class CommonPd {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	private String value;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "document_id", nullable = false)
	private DocumentPersistenceData srcDoc;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "individual_id")
	private Individual individual;

	public CommonPd(String value, DocumentPersistenceData srcDoc, Individual individual) {
		this.value = value;
		this.srcDoc = srcDoc;
		this.individual = individual;
	}
}

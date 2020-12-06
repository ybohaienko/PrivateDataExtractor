package com.bohaienko.pdextractor.model.pdTypeValues;

import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.model.DocumentPersistenceData;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class WorkingPlace extends CommonPd {
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String value;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "document_id", nullable = false)
	private DocumentPersistenceData srcDoc;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "individual_id")
	private Individual individual;

	public WorkingPlace(String value, DocumentPersistenceData srcDoc, Individual individual) {
		this.value = value;
		this.srcDoc = srcDoc;
		this.individual = individual;
	}
}
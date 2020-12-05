package com.bohaienko.pdextractor.model.individual;

import com.bohaienko.pdextractor.model.common.DocumentPersistenceData;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class FathersName extends CommonPd {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String value;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "document_id", nullable = false)
	private DocumentPersistenceData srcDoc;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "individual_id", nullable = false)
	private Individual individual;

	public FathersName(String value, DocumentPersistenceData srcDoc, Individual individual) {
		super();
		this.value = value;
		this.srcDoc = srcDoc;
		this.individual = individual;
	}
}

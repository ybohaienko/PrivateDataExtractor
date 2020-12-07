package com.bohaienko.pdextractor.model.pdTypeValues;

import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.model.SourceDocument;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Website extends BasePdTypeValue {
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String value;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "document_id", nullable = false)
	private SourceDocument document;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "individual_id")
	private Individual individual;

	public Website(String value, String pdType, SourceDocument document, Individual individual) {
		this.value = value;
		this.document = document;
		this.individual = individual;
	}
}

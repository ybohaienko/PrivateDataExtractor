package com.bohaienko.pdextractor.model.individual;

import com.bohaienko.pdextractor.model.common.DocumentPersistenceData;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Profession {
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String value;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "document_id", nullable = false)
	private DocumentPersistenceData srcDoc;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "individual_id", nullable = false)
	private Individual individual;
}

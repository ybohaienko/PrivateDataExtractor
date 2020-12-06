package com.bohaienko.pdextractor.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(
		columnNames = {
				"colPosNum",
				"colHeader",
				"colPdType"
		}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class SourceColumn {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/* column position number */
	@NotNull
	int colPosNum;
	@NotNull
	String colHeader;
	/* column expected private data type */
	@NotNull
	String colPdType;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "document_id", nullable = false)
	private SourceDocument document;

	public SourceColumn(int colPosNum, String colHeader, String colPdType, SourceDocument document) {
		this.colPosNum = colPosNum;
		this.colHeader = colHeader;
		this.colPdType = colPdType;
		this.document = document;
	}
}

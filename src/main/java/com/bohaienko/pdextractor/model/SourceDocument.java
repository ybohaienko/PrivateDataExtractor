package com.bohaienko.pdextractor.model;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(
		columnNames = {
				"documentName",
				"documentPath"
		}))
@Getter
@Setter
@Transactional
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class SourceDocument {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	String documentName;
	@NotNull
	String documentPath;

	public SourceDocument(String documentName, String documentPath) {
		this.documentName = documentName;
		this.documentPath = documentPath;
	}
}

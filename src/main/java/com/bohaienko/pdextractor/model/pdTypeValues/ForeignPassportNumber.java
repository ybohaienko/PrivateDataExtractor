package com.bohaienko.pdextractor.model.pdTypeValues;

import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.model.SourceDocument;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ForeignPassportNumber extends BasePdTypeValue {
	public ForeignPassportNumber(String value, SourceDocument document, Individual individual) {
		super(value, document, individual);
	}
}

package com.bohaienko.pdextractor.model.pdTypeValues;

import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.model.SourceDocument;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IdTaxNumber extends BasePdTypeValue {
	public IdTaxNumber(String value, String pdType, SourceDocument document, Individual individual) {
		super(value, pdType, document, individual);
	}
}

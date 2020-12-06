package com.bohaienko.pdextractor.model.pdTypeValues;

import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.model.SourceDocument;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddressAptNumber extends BasePdTypeValue {
	public AddressAptNumber(String value, SourceDocument document, Individual individual) {
		super(value, document, individual);
	}
}

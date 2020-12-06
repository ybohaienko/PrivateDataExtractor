package com.bohaienko.pdextractor.model.pdTypeValues;

import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.model.SourceDocument;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddressCountry extends BasePdTypeValue {
	public AddressCountry(String value, SourceDocument document, Individual individual) {
		super(value, document, individual);
	}
}

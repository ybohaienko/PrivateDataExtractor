package com.bohaienko.pdextractor.model.pdTypeValues;

import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.model.SourceDocument;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddressStreetNumber extends CommonPd {
	public AddressStreetNumber(String value, SourceDocument srcDoc, Individual individual) {
		super(value, srcDoc, individual);
	}
}

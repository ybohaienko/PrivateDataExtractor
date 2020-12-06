package com.bohaienko.pdextractor.model.pdTypeValues;

import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.model.DocumentPersistenceData;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddressAptNumber extends CommonPd {
	public AddressAptNumber(String value, DocumentPersistenceData srcDoc, Individual individual) {
		super(value, srcDoc, individual);
	}
}
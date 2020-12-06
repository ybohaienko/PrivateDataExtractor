package com.bohaienko.pdextractor.model.pdTypeValues;

import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.model.DocumentPersistenceData;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddressCity extends CommonPd {
	public AddressCity(String value, DocumentPersistenceData srcDoc, Individual individual) {
		super(value, srcDoc, individual);
	}
}

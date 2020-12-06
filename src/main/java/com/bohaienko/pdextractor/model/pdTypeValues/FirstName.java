package com.bohaienko.pdextractor.model.pdTypeValues;

import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.model.SourceDocument;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FirstName extends CommonPd {
	public FirstName(String value, SourceDocument srcDoc, Individual individual) {
		super(value, srcDoc, individual);
	}
}

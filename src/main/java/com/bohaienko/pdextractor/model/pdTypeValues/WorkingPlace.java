package com.bohaienko.pdextractor.model.pdTypeValues;

import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.model.SourceDocument;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkingPlace extends BasePdTypeValue {
	public WorkingPlace(String value, String pdType, SourceDocument document, Individual individual) {
		super(value, pdType, document, individual);
	}
}

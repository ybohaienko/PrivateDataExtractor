package com.bohaienko.pdextractor.model.individual;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Individual {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
}

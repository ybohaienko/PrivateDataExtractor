package com.bohaienko.pdextractor.model.occasional;

import com.bohaienko.pdextractor.model.PrivateDataType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PrivateDataValue {
	@NonNull
	PrivateDataType type;
	@NonNull
	String value;
	@NonNull
	String fullPath;
}

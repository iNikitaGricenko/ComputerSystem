package com.wolfhack.cloud.product.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "database_sequences")
public class DatabaseSequence {

	@Transient public static final String SEQUENCE_NAME = "products_sequence";

	@Id private String id;

	private Long sequence;

}

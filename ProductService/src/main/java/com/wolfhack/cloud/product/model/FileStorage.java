package com.wolfhack.cloud.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("file_storage")
public class FileStorage {

	private String id;

	private String key;

	@JsonProperty("file_name")
	private String name;

	@JsonProperty("file_size")
	private double size;

	@JsonProperty("file_type")
	private String type;

	@JsonProperty("file_url")
	private String url;

}

package com.wolfhack.cloud.product.model.search;

import com.wolfhack.cloud.product.model.Cpu;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Search model for {@link Cpu}
 */
@Value
@Document(indexName = "product-cpu")
public class CpuSearch implements Serializable {
	@Id
	@NotNull @Min(0) Long id;

	@Field(type = FieldType.Text, fielddata = true)
	@NotNull @Size(min = 5) String name;

	@Field(type = FieldType.Text, fielddata = true)
	@NotNull @Size(min = 5) String model;

	@Field(type = FieldType.Long)
	long cores;

	@Field(type = FieldType.Long)
	long threads;

	@Field(type = FieldType.Long)
	long cacheSize;

	@Field(type = FieldType.Float)
	float frequency;

	@Field(type = FieldType.Text, fielddata = true)
	String microarchitecture;

	@Field(type = FieldType.Text, fielddata = true)
	String series;

	@Field(type = FieldType.Text, fielddata = true)
	String graphics;

	@Field(type = FieldType.Text, fielddata = true)
	String socket;

	@Field(type = FieldType.Text, fielddata = true)
	String compatibility;

	@Field(type = FieldType.Text, fielddata = true)
	String productLine;
}
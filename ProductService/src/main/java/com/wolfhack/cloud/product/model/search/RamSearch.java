package com.wolfhack.cloud.product.model.search;

import com.wolfhack.cloud.product.model.Ram;
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
 * Search model for {@link Ram}
 */
@Value
@Document(indexName = "product-ram")
public class RamSearch implements Serializable {
	@Id
	@NotNull @Min(0) Long id;

	@Field(type = FieldType.Text, fielddata = true)
	@NotNull @Size(min = 5) String title;

	@Field(type = FieldType.Text, fielddata = true)
	@NotNull @Size(min = 5) String name;

	@Field(type = FieldType.Text, fielddata = true)
	@NotNull @Size(min = 5) String model;

	@Field(type = FieldType.Text, fielddata = true) String type;

	@Field(type = FieldType.Long) long size;

	@Field(type = FieldType.Text, fielddata = true) String formFactor;

	@Field(type = FieldType.Float) float freq;

	@Field(type = FieldType.Double) double bandwidth;

	@Field(type = FieldType.Text, fielddata = true) String casLatency;

	@Field(type = FieldType.Text, fielddata = true) String timingScheme;

	@Field(type = FieldType.Text, fielddata = true) String eccMemory;

	@Field(type = FieldType.Text, fielddata = true) String xmp;

	@Field(type = FieldType.Float) float voltage;

	@Field(type = FieldType.Text, fielddata = true) String worksTemperature;

	@Field(type = FieldType.Text, fielddata = true) String storageTemperature;
}
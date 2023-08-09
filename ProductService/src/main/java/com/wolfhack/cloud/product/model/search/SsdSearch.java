package com.wolfhack.cloud.product.model.search;

import com.wolfhack.cloud.product.model.Ssd;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;

/**
 * Search model for {@link Ssd}
 */
@Value
@Document(indexName = "products-ssd")
public class SsdSearch implements Serializable {
	@Id
	@NotNull @Min(0) Long id;

	@Field(type = FieldType.Text, fielddata = true)
	@NotNull @Size(min = 5) String title;

	@Field(type = FieldType.Text, fielddata = true)
	@NotNull @Size(min = 5) String name;

	@Field(type = FieldType.Text, fielddata = true)
	@NotNull @Size(min = 5) String model;

	@Field(type = FieldType.Text, fielddata = true) String formFactor;

	@Field(type = FieldType.Text, fielddata = true) String memorySize;

	@Field(type = FieldType.Text, fielddata = true) String memorySlotsType;

	@Field(type = FieldType.Text, fielddata = true) String interfaceType;

	@Field(type = FieldType.Text, fielddata = true) String nvme;

	@Field(type = FieldType.Text, fielddata = true) String controller;

	@Field(type = FieldType.Long) long readerSpeed;

	@Field(type = FieldType.Long) long writerSpeed;

	@Field(type = FieldType.Text, fielddata = true) String MTBF;

	@Field(type = FieldType.Double) double impactResistance;

	@Field(type = FieldType.Float) float powerRequirement;

	@Field(type = FieldType.Text, fielddata = true) String worksTemperature;

	@Field(type = FieldType.Text, fielddata = true) String storageTemperature;

	HashSet<String> additionally;

}
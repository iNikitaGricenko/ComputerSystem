package com.wolfhack.cloud.product.model.search;

import com.wolfhack.cloud.product.model.Gpu;
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
 * Search model for {@link Gpu}
 */
@Value
@Document(indexName = "product-gpu")
public class GpuSearch implements Serializable {
	@Id
	@NotNull @Min(0) Long id;

	@Field(type = FieldType.Text, fielddata = true)
	@NotNull @Size(min = 5) String title;

	@Field(type = FieldType.Text, fielddata = true)
	@NotNull @Size(min = 5) String name;

	@Field(type = FieldType.Text, fielddata = true)
	@NotNull @Size(min = 5) String model;

	@Field(type = FieldType.Text, fielddata = true) String type;

	@Field(type = FieldType.Long) long memorySize;

	@Field(type = FieldType.Text, fielddata = true) String memoryType;

	@Field(type = FieldType.Double) double memoryBus;

	@Field(type = FieldType.Text, fielddata = true) String graphicsProcessor;

	@Field(type = FieldType.Double) double coreFrequency;

	@Field(type = FieldType.Double) double videoMemoryFrequency;

	@Field(type = FieldType.Text, fielddata = true) String maxResolution;

	@Field(type = FieldType.Text, fielddata = true) String processorFamily;

	@Field(type = FieldType.Boolean) boolean cudaSupport;

	@Field(type = FieldType.Boolean) boolean additionalPowerSupply;

	@Field(type = FieldType.Double) double length;

	@Field(type = FieldType.Double) double height;

	@Field(type = FieldType.Double) double powerSupplyCapacity;

	@Field(type = FieldType.Integer) int fans;

	@Field(type = FieldType.Integer) int cudaCores;

	@Field(type = FieldType.Text, fielddata = true) String supplyConnector;

	@Field(type = FieldType.Text, fielddata = true) String gpuInterface;
}
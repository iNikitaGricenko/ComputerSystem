package com.wolfhack.cloud.product.model.search;

import com.wolfhack.cloud.product.model.Motherboard;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Search model for {@link Motherboard}
 */
@Value
@Document(indexName = "product-motherboard")
public class MotherboardSearch implements Serializable {
	@Id
	@NotNull @Min(0) Long id;

	@Field(type = FieldType.Text, fielddata = true)
	@NotNull @Size(min = 5) String name;

	@Field(type = FieldType.Text, fielddata = true)
	@NotNull @Size(min = 5) String model;

	@Field(type = FieldType.Text, fielddata = true)
	String type;

	@Field(type = FieldType.Text, fielddata = true)
	String socket;

	@Field(type = FieldType.Text, fielddata = true)
	String cpus;

	@Field(type = FieldType.Text, fielddata = true)
	String chipset;

	@Field(type = FieldType.Text, fielddata = true)
	String memoryType;

	@Field(type = FieldType.Text, fielddata = true)
	String compatibleRam;

	@Field(type = FieldType.Integer)
	int ramSlots;

	@Field(type = FieldType.Integer)
	int channelNumber;

	@Field(type = FieldType.Double)
	double maxRamCapacity;

	@Field(type = FieldType.Double)
	double minRamFreq;

	@Field(type = FieldType.Double)
	double maxRamFreq;

	@Field(type = FieldType.Text, fielddata = true)
	String wirelessAdapter;

	@Field(type = FieldType.Text, fielddata = true)
	String soundCard;

	@Field(type = FieldType.Float)
	float soundScheme;

	HashMap<String, Integer> injectedPorts = new HashMap<>();

	HashMap<String, Integer> powerConnectors = new HashMap<>();

	HashMap<String, Integer> externalPorts = new HashMap<>();

	@Field(type = FieldType.Text, fielddata = true)
	String formFactor;
}
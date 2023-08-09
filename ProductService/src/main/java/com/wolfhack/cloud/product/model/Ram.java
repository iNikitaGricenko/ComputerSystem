package com.wolfhack.cloud.product.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class Ram {

	@NotNull
	@Size(min = 5)
	private String name;
	@NotNull
	@Size(min = 5)
	private String model;
	private String description;

	private String type;
	private long size;
	private String formFactor;
	private float freq;
	private double bandwidth;
	private String casLatency;
	private String timingScheme;
	private String eccMemory;
	private String xmp;
	private float voltage;

	private String worksTemperature;
	private String storageTemperature;

}

package com.wolfhack.cloud.product.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;

@Data
public class Ssd {

	@NotNull
	@Size(min = 5)
	private String name;
	@NotNull
	@Size(min = 5)
	private String model;
	private String description;

	private String formFactor;
	private String memorySize;
	private String memorySlotsType;
	private String interfaceType;
	private String nvme;
	private String controller;
	private long readerSpeed;
	private long writerSpeed;

	private String MTBF; // Время наработки на отказ
	private double impactResistance;
	private float powerRequirement;
	private String worksTemperature;
	private String storageTemperature;

	private HashSet<String> additionally;

}

package com.wolfhack.cloud.product.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class Gpu {

	@NotNull
	@Size(min = 5)
	private String name;
	@NotNull
	@Size(min = 5)
	private String model;
	private String description;

	private String type;
	private long memorySize;
	private String memoryType;
	private double memoryBus;

	private String graphicsProcessor;
	private double coreFrequency;
	private double videoMemoryFrequency;
	private String maxResolution;
	private String processorFamily;

	private boolean cudaSupport;
	private boolean additionalPowerSupply;

	private double length;
	private double height;
	private double powerSupplyCapacity;

	private int fans;
	private int cudaCores;

	private String[] standardsSupports;
	private String supplyConnector;

	private String gpuInterface;
	private String[] connectors;

}

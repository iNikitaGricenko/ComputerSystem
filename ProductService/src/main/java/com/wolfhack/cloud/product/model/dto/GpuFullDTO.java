package com.wolfhack.cloud.product.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GpuFullDTO implements Serializable {
	private final String name;
	private final String model;
	private final int quantity;
	private final float unitPrice;
	private final String[] photosNames;
	private final String type;
	private final long memorySize;
	private final String memoryType;
	private final double memoryBus;
	private final String graphicsProcessor;
	private final double coreFrequency;
	private final double videoMemoryFrequency;
	private final String maxResolution;
	private final String processorFamily;
	private final boolean cudaSupport;
	private final boolean additionalPowerSupply;
	private final double length;
	private final double height;
	private final double powerSupplyCapacity;
	private final int fans;
	private final int cudaCores;
	private final String[] standardsSupports;
	private final String supplyConnector;
	private final String gpuInterface;
	private final String[] connectors;

}

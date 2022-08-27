package com.wolfhack.cloud.product.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RamResponseDTO implements Serializable {
    private final Double id;
    private final String name;
    private final String model;
    private final double cost;
    private final String[] photosNames;
    private final String type;
    private final long size;
    private final String formFactor;
    private final float freq;
    private final double bandwidth;
    private final String casLatency;
    private final String timingScheme;
    private final String eccMemory;
    private final String xmp;
    private final float voltage;
    private final String worksTemperature;
    private final String storageTemperature;
}

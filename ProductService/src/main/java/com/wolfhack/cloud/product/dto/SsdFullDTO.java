package com.wolfhack.cloud.product.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;

@Data
public class SsdFullDTO implements Serializable {
    private final String name;
    private final String model;
    private final double cost;
    private final String[] photosNames;
    private final String formFactor;
    private final String memorySize;
    private final String memorySlotsType;
    private final String interfaceType;
    private final String nvme;
    private final String controller;
    private final long readerSpeed;
    private final long writerSpeed;
    private final String MTBF;
    private final double impactResistance;
    private final float powerRequirement;
    private final String worksTemperature;
    private final String storageTemperature;
    private final HashSet<String> additionally;
}

package com.wolfhack.cloud.product.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CpuResponseDTO implements Serializable {
    private final Double id;
    private final String name;
    private final String model;
    private final double cost;
    private final String[] photosNames;
    private final long cores;
    private final long threads;
    private final long cacheSize;
    private final float frequency;
    private final String microarchitecture;
    private final String series;
    private final String graphics;
    private final String socket;
    private final String compatibility;
    private final String productLine;

}

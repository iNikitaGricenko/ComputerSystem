package com.wolfhack.cloud.product.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Document("gpus")
public class Gpu {

    @NotNull @Min(0)
    private Long id;
    @NotNull @Size(min = 5)
    private String name;
    @NotNull @Size(min = 5)
    private String model;
    @NotNull @Min(5)
    private int quantity;
    @NotNull @Min(0)
    private float unitPrice;
    private String[] photosNames;

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

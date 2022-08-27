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
import java.util.HashSet;

@Data
@Document("ssds")
public class Ssd {

    @MongoId(FieldType.DECIMAL128)
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

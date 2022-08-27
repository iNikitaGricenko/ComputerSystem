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
@Document("rams")
public class Ram {

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

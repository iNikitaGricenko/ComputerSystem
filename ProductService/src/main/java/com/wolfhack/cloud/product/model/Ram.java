package com.wolfhack.cloud.product.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Document("rams")
public class Ram {

    @MongoId(FieldType.DOUBLE)
    @NotNull @Min(0)
    private Double id;
    @NotNull @Size(min = 5)
    private String name;
    @NotNull @Size(min = 5)
    private String model;
    @NotNull @Min(5)
    private double cost;
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

package com.wolfhack.cloud.product.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Document("rams")
public class Ram {

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
    private String description;

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

    private List<FileStorage> photos;
}

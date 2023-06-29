package com.wolfhack.cloud.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Document("cpus")
@NoArgsConstructor
@AllArgsConstructor
public class Cpu {

    @NotNull @Min(0)
    private Long id;
    @NotNull
    @Size(min = 5)
    private String name;
    @NotNull
    @Size(min = 5)
    private String model;
    @NotNull @Min(5)
    private int quantity;
    @NotNull @Min(0)
    private float unitPrice;
    private String[] photosNames;

    private long cores;
    private long threads;
    private long cacheSize;
    private float frequency;
    private String microarchitecture;
    private String series;
    private String graphics;
    private String socket;
    private String compatibility;
    private String productLine;
}

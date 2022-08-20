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
import java.util.HashMap;

@Data
@Document("motherboards")
public class Motherboard {

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
    private String socket;
    private String cpus;
    private String chipset;

    private String memoryType;
    private String compatibleRam;
    private int ramSlots;
    private int channelNumber;
    private double maxRamCapacity;
    private double minRamFreq;
    private double maxRamFreq;

    private String wirelessAdapter;

    private String soundCard;
    private float soundScheme;

    private HashMap<String, Integer> injectedPorts = new HashMap<>();

    private HashMap<String, Integer> powerConnectors = new HashMap<>();

    private HashMap<String, Integer> externalPorts = new HashMap<>();

    private String formFactor;
}

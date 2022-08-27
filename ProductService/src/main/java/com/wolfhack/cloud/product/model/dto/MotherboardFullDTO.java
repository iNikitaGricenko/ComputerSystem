package com.wolfhack.cloud.product.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

@Data
public class MotherboardFullDTO implements Serializable {
    private final String name;
    private final String model;
    private final int quantity;
    private final float unitPrice;
    private final String[] photosNames;
    private final String type;
    private final String socket;
    private final String cpus;
    private final String chipset;
    private final String memoryType;
    private final String compatibleRam;
    private final int ramSlots;
    private final int channelNumber;
    private final double maxRamCapacity;
    private final double minRamFreq;
    private final double maxRamFreq;
    private final String wirelessAdapter;
    private final String soundCard;
    private final float soundScheme;
    private final HashMap<String, Integer> injectedPorts;
    private final HashMap<String, Integer> powerConnectors;
    private final HashMap<String, Integer> externalPorts;
    private final String formFactor;
}

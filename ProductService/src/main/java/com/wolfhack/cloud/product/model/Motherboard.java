package com.wolfhack.cloud.product.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;

@Data
public class Motherboard {

	@NotNull
	@Size(min = 5)
	private String name;
	@NotNull
	@Size(min = 5)
	private String model;
	@NotNull
	@Min(5)
	private int quantity;
	@NotNull
	@Min(0)
	private float unitPrice;
	private String description;

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

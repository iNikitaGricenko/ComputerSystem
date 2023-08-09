package com.wolfhack.cloud.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cpu {

	@NotNull
	@Size(min = 5)
	private String name;
	@NotNull
	@Size(min = 5)
	private String model;
	private String description;

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

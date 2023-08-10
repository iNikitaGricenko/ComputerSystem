package com.wolfhack.cloud.email.model;

import lombok.Data;

@Data
public class Order {

	private String title;
	private String description;
	private double price;
	private double count;

}

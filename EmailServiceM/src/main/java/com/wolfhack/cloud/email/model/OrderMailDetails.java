package com.wolfhack.cloud.email.model;

import lombok.Value;

import java.util.Collection;

@Value
public class OrderMailDetails {

	String recipient;
	Collection<Order> orders;

}

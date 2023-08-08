package com.wolfhack.cloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public final class EntityOrderItem {

	private Long id;

	@Column(name = "order_item_name") private String name;

	@Column(name = "order_item_model") private String model;

	@Column(name = "quantity") private long quantity;

	@Column(name = "unit_price") private double unitPrice;

	@Column(name = "order_item_class_name") private String className;
}

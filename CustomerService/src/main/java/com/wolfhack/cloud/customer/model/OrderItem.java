package com.wolfhack.cloud.customer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private Long id;

    @Column(name = "order_item_name")
    private String name;

    @Column(name = "order_item_model")
    private String model;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unit_price")
    private float unitPrice;

    @Column(name = "order_item_class_name")
    private String className;
}

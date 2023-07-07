package com.wolfhack.cloud.customer.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AnalyticsResponse implements Serializable {

    private final double totalPrice;
    private final long totalQuantity;

    private final double maxOrderPrice;
    private final double minOrderPrice;

}

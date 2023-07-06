package com.wolfhack.cloud.customer.model.enums;

public enum OrderStatus {
    INPROGRESS, PENDING, DELIVERED, RETURNED;

    @Override
    public String toString() {
        return name();
    }
}

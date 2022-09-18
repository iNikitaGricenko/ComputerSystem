package com.wolfhack.cloud.customer.model.enums;

public enum OrderStatus {
    INPROGRESS, PENDING, DELIVERED, RETURN;

    @Override
    public String toString() {
        return name();
    }
}

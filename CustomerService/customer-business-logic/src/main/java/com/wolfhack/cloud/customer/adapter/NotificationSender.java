package com.wolfhack.cloud.customer.adapter;

import com.wolfhack.cloud.customer.model.OrderItem;

public interface NotificationSender {

    void send(OrderItem customerOrder);

}

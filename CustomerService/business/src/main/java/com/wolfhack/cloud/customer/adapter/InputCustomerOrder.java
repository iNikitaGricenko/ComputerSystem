package com.wolfhack.cloud.customer.adapter;

import com.wolfhack.cloud.customer.model.CustomerOrder;

public interface InputCustomerOrder {

    CustomerOrder persist(CustomerOrder customerOrder);

}

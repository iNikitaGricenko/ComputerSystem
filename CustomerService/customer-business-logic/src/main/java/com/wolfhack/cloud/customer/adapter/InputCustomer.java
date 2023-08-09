package com.wolfhack.cloud.customer.adapter;

import com.wolfhack.cloud.customer.model.Customer;

public interface InputCustomer {

	Customer persist(Customer customerOrder);

	Customer update(Long id, Customer customer);
}

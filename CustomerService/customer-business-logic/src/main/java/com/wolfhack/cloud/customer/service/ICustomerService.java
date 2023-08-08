package com.wolfhack.cloud.customer.service;

import com.wolfhack.cloud.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService {

	Customer save(Customer customer);

	Customer findById(Long id);

	Page<Customer> findAll(Pageable pageable);
}

package com.wolfhack.cloud.customer.adapter;

import com.wolfhack.cloud.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OutputCustomer {

	Optional<Customer> get(Long id);

	Page<Customer> getAll(Pageable pageable);

}

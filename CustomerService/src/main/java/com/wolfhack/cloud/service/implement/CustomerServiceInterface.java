package com.wolfhack.cloud.service.implement;

import com.wolfhack.cloud.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerServiceInterface {
    Customer save(Customer customer);

    Customer findById(Long id);

    Page<Customer> findAll(Pageable pageable);
}

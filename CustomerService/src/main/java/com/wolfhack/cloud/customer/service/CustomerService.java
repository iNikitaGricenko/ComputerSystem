package com.wolfhack.cloud.customer.service;

import com.wolfhack.cloud.customer.model.Customer;
import com.wolfhack.cloud.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface CustomerService {

    @Service
    @RequiredArgsConstructor
    final class CustomerServiceImpl implements CustomerService {
        private final CustomerRepository customerRepository;

        @Override
        public Customer save(Customer customer) {
            return customerRepository.save(customer);
        }

        @Override
        public Customer findById(Long id) {
            return customerRepository.findById(id).orElseThrow();
        }

        @Override
        public Page<Customer> findAll(Pageable pageable) {
            return customerRepository.findAll(pageable);
        }
    }

    Customer save(Customer customer);

    Customer findById(Long id);

    Page<Customer> findAll(Pageable pageable);
}

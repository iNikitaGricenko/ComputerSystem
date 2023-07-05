package com.wolfhack.cloud.gateway;

import com.wolfhack.cloud.customer.adapter.OutputCustomer;
import com.wolfhack.cloud.customer.model.Customer;
import com.wolfhack.cloud.mapper.EntityCustomerMapper;
import com.wolfhack.cloud.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OutputCustomerGateway implements OutputCustomer {

    private final CustomerRepository customerRepository;
    private final EntityCustomerMapper entityCustomerMapper;

    @Override
    public Optional<Customer> get(Long id) {
        return customerRepository.findById(id).map(entityCustomerMapper::toBusinessFromEntity);
    }

    @Override
    public Page<Customer> getAll(Pageable pageable) {
        return customerRepository.findAll(pageable).map(entityCustomerMapper::toBusinessFromEntity);
    }
}

package com.wolfhack.cloud.gateway;

import com.wolfhack.cloud.customer.adapter.InputCustomer;
import com.wolfhack.cloud.customer.model.Customer;
import com.wolfhack.cloud.entity.EntityCustomer;
import com.wolfhack.cloud.mapper.EntityCustomerMapper;
import com.wolfhack.cloud.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InputCustomerGateway implements InputCustomer {

    private final CustomerRepository customerRepository;
    private final EntityCustomerMapper entityCustomerMapper;

    @Override
    public Customer persist(Customer customer) {
        EntityCustomer entity = entityCustomerMapper.toEntityFromBusiness(customer);
        return entityCustomerMapper.toBusinessFromEntity(customerRepository.save(entity));
    }
}

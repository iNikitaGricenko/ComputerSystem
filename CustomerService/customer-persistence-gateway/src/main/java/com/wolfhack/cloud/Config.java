package com.wolfhack.cloud;

import com.wolfhack.cloud.customer.adapter.InputCustomer;
import com.wolfhack.cloud.gateway.InputCustomerGateway;
import com.wolfhack.cloud.mapper.EntityCustomerMapper;
import com.wolfhack.cloud.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class Config {

    private final CustomerRepository repository;
    private final EntityCustomerMapper entityCustomerMapper;

    @Bean
    public InputCustomer inputCustomer() {
        return new InputCustomerGateway(repository, entityCustomerMapper);
    }

}

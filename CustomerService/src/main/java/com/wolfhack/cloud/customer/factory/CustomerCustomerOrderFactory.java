package com.wolfhack.cloud.customer.factory;

import com.wolfhack.cloud.customer.model.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.model.dto.CustomerOrderResponseDTO;
import com.wolfhack.cloud.customer.factory.mapper.CustomerOrderMapper;
import com.wolfhack.cloud.customer.factory.implement.CustomerOrderFactoryInterface;
import com.wolfhack.cloud.customer.model.Customer;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerCustomerOrderFactory implements CustomerOrderFactoryInterface {

    private final CustomerOrderMapper customerOrderMapper;

    @Override
    public CustomerOrder create(CustomerOrderRequestDTO requestDTO) {
        CustomerOrder customerOrder = customerOrderMapper.toOrderFromRequest(requestDTO);

        customerOrder.setCreated(LocalDateTime.now());
        Customer customer = customerOrder.getCustomer();
        customer.setEmail(customer.getEmail().toLowerCase());

        return customerOrder;
    }

    @Override
    public CustomerOrderResponseDTO create(CustomerOrder customerOrder) {
        return customerOrderMapper.toResponseDTO(customerOrder);
    }

    @Override
    public CustomerOrder edit(CustomerOrder customerOrder, CustomerOrder editor) {
        Optional.ofNullable(editor.getCustomer()).ifPresent(customerOrder::setCustomer);
        Optional.ofNullable(editor.getAddress()).ifPresent(customerOrder::setAddress);
        Optional.ofNullable(editor.getOrderItems()).ifPresent(customerOrder::setOrderItems);
        Optional.ofNullable(editor.getDescription()).ifPresent(customerOrder::setDescription);
        Optional.ofNullable(editor.getCity()).ifPresent(customerOrder::setCity);
        Optional.ofNullable(editor.getPaymentMethod()).ifPresent(customerOrder::setPaymentMethod);
        Optional.ofNullable(editor.getState()).ifPresent(customerOrder::setState);
        Optional.ofNullable(editor.getStatus()).ifPresent(customerOrder::setStatus);
        Optional.ofNullable(editor.getZipCode()).ifPresent(customerOrder::setZipCode);
        return customerOrder;
    }
}

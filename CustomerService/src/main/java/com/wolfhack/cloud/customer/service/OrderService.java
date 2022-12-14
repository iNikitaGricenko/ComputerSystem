package com.wolfhack.cloud.customer.service;

import com.wolfhack.cloud.customer.model.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.model.dto.CustomerOrderResponseDTO;
import com.wolfhack.cloud.customer.exception.CustomerOrderNotFoundException;
import com.wolfhack.cloud.customer.factory.implement.CustomerOrderFactoryInterface;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.repository.OrderRepository;
import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import com.wolfhack.cloud.customer.service.implement.OrderKafkaSenderInterface;
import com.wolfhack.cloud.customer.service.implement.OrderServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceInterface {

    private final OrderRepository orderRepository;
    private final CustomerOrderFactoryInterface customerOrderFactory;
    private final OrderKafkaSenderInterface orderKafkaSender;

    @Override
    public CustomerOrderResponseDTO save(CustomerOrderRequestDTO requestDTO) {
        CustomerOrder customerOrder = customerOrderFactory.create(requestDTO);

        CustomerOrderResponseDTO responseDTO = customerOrderFactory.create(orderRepository.save(customerOrder));

        sendToKafka(responseDTO);
        return responseDTO;
    }

    @Async
    protected void sendToKafka(CustomerOrderResponseDTO responseDTO) {
        responseDTO.getOrderItems().forEach(orderKafkaSender::send);
    }

    @Override
    public CustomerOrderResponseDTO changeStatus(Long id, OrderStatus status) {
        CustomerOrder customerOrder = orderRepository.findById(id)
                .map(order -> customerOrderFactory.edit(order, CustomerOrder.builder().status(status).build()))
                .orElseThrow(CustomerOrderNotFoundException::new);
        return customerOrderFactory.create(orderRepository.save(customerOrder));
    }

    @Override
    public CustomerOrderResponseDTO findById(Long id) {
        return customerOrderFactory.create(orderRepository.findById(id)
                .orElseThrow(CustomerOrderNotFoundException::new));
    }

    @Override
    public Page<CustomerOrderResponseDTO> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(customerOrderFactory::create);
    }
}

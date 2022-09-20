package com.wolfhack.cloud.customer.service;

import com.wolfhack.cloud.customer.exception.CustomerOrderNotFoundException;
import com.wolfhack.cloud.customer.factory.CustomerOrderFactory;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.model.dto.*;
import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import com.wolfhack.cloud.customer.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface OrderService {

    @Slf4j
    @Service
    @RequiredArgsConstructor
    final class OrderServiceImpl implements OrderService {

        private final OrderRepository orderRepository;
        private final CustomerOrderFactory customerOrderFactory;
        private final OrderKafkaSender orderKafkaSender;

        @Override
        public CustomerOrderResponseDTO save(CustomerOrderRequestDTO requestDTO) {
            CustomerOrder customerOrder = customerOrderFactory.create(requestDTO);

            CustomerOrderResponseDTO responseDTO = customerOrderFactory.create(orderRepository.save(customerOrder));

            sendToKafka(responseDTO);
            return responseDTO;
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

        @Override
        public AnalyticsResponseDTO getAnalytics(AnalyticsSearchDTO analyticsSearchDTO) {
            try {
                OrderStatus status = analyticsSearchDTO.getStatus();
                LocalDateTime from = analyticsSearchDTO.getFrom().atTime(LocalTime.MIN);
                LocalDateTime to = analyticsSearchDTO.getTo().atTime(LocalTime.MIN);
                List<CustomerOrder> orderItems =
                        orderRepository.findAllByStatusAndCompletedBetween(status, from, to);
                return customerOrderFactory.create(orderItems);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException("The server was unable to collect analytics");
            }
        }

        private void sendToKafka(CustomerOrderResponseDTO responseDTO) {
            responseDTO.getOrderItems().forEach(orderKafkaSender::send);
        }
    }

    CustomerOrderResponseDTO save(CustomerOrderRequestDTO requestDTO);

    CustomerOrderResponseDTO changeStatus(Long id, OrderStatus status);

    CustomerOrderResponseDTO findById(Long id);

    Page<CustomerOrderResponseDTO> findAll(Pageable pageable);

    AnalyticsResponseDTO getAnalytics(AnalyticsSearchDTO analyticsSearchDTO);
}

package com.wolfhack.cloud.gateway;

import com.wolfhack.cloud.customer.adapter.OutputCustomerOrder;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import com.wolfhack.cloud.entity.EntityCustomerOrder;
import com.wolfhack.cloud.mapper.EntityCustomerOrderMapper;
import com.wolfhack.cloud.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OutputOrderGateway implements OutputCustomerOrder {

    private final OrderRepository orderRepository;
    private final EntityCustomerOrderMapper orderMapper;

    @Override
    public Optional<CustomerOrder> get(Long id) {
        return orderRepository.findById(id).map(orderMapper::toBusinessFromEntity);
    }

    @Override
    public List<CustomerOrder> getAllByStatusAndCompletedBetween(OrderStatus status, LocalDateTime from, LocalDateTime to) {
        List<EntityCustomerOrder> allByStatusAndCompletedBetween = orderRepository.findAllByStatusAndCompletedBetween(status, from, to);
        return allByStatusAndCompletedBetween.stream()
                .map(orderMapper::toBusinessFromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CustomerOrder> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toBusinessFromEntity);
    }
}

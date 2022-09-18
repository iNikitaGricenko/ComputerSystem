package com.wolfhack.cloud.customer.factory;

import com.wolfhack.cloud.customer.model.OrderItem;
import com.wolfhack.cloud.customer.model.dto.AnalyticsResponseDTO;
import com.wolfhack.cloud.customer.model.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.model.dto.CustomerOrderResponseDTO;
import com.wolfhack.cloud.customer.factory.mapper.CustomerOrderMapper;
import com.wolfhack.cloud.customer.factory.implement.CustomerOrderFactoryInterface;
import com.wolfhack.cloud.customer.model.Customer;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerOrderFactory implements CustomerOrderFactoryInterface {

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

    @Override
    public AnalyticsResponseDTO create(List<CustomerOrder> customerOrders) throws ExecutionException, InterruptedException {
        Set<OrderItem> orderItems = new HashSet<>();
        customerOrders.stream().map(CustomerOrder::getOrderItems).forEach(orderItems::addAll);
        Callable<Long> totalQuantityCallable = () -> orderItems.stream().mapToLong(OrderItem::getQuantity).sum();
        Callable<Double> totalPriceCallable = () -> orderItems.stream()
                .mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity())
                .sum();
        Callable<Double> maxPriceCallable = () -> orderItems.stream()
                .mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity())
                .max().orElse(0);
        Callable<Double> minPriceCallable = () -> orderItems.stream()
                .mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity())
                .min().orElse(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Long> totalQuantityFuture = executor.submit(totalQuantityCallable);
        Future<Double> totalPriceFuture = executor.submit(totalPriceCallable);
        Future<Double> maxPriceFuture = executor.submit(maxPriceCallable);
        Future<Double> minPriceFuture = executor.submit(minPriceCallable);

        Double totalPrice = totalPriceFuture.get();
        Long totalQuantity = totalQuantityFuture.get();
        Double maxPrice = maxPriceFuture.get();
        Double minPrice = minPriceFuture.get();
        return new AnalyticsResponseDTO(totalPrice, totalQuantity, maxPrice, minPrice);
    }


}

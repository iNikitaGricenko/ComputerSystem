package com.wolfhack.cloud.customer.factory;

import com.wolfhack.cloud.customer.bean.Factory;
import com.wolfhack.cloud.customer.factory.mapper.CustomerOrderMapper;
import com.wolfhack.cloud.customer.model.Customer;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.model.OrderItem;
import com.wolfhack.cloud.customer.model.dto.AnalyticsResponseDTO;
import com.wolfhack.cloud.customer.model.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.model.dto.CustomerOrderResponseDTO;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;

public interface CustomerOrderFactory {

    @Factory
    @RequiredArgsConstructor
    final class CustomerOrderFactoryImpl implements CustomerOrderFactory {

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
            return customerOrder.renovator()
                    .edit(editor, CustomerOrder::getCustomer, customerOrder::setCustomer)
                    .edit(editor, CustomerOrder::getAddress, customerOrder::setAddress)
                    .edit(editor, CustomerOrder::getOrderItems, customerOrder::setOrderItems)
                    .edit(editor, CustomerOrder::getDescription, customerOrder::setDescription)
                    .edit(editor, CustomerOrder::getCity, customerOrder::setCity)
                    .edit(editor, CustomerOrder::getPaymentMethod, customerOrder::setPaymentMethod)
                    .edit(editor, CustomerOrder::getPaymentCurrency, customerOrder::setPaymentCurrency)
                    .edit(editor, CustomerOrder::getState, customerOrder::setState)
                    .edit(editor, CustomerOrder::getStatus, customerOrder::setStatus)
                    .edit(editor, CustomerOrder::getZipCode, customerOrder::setZipCode)
                    .update();
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

        private static <T> void update(
                CustomerOrder editor, Function<CustomerOrder, T> getMethod, Consumer<T> setMethod) {
            Optional.ofNullable(getMethod.apply(editor)).ifPresent(setMethod);
        }
    }

    CustomerOrder create(CustomerOrderRequestDTO requestDTO);

    CustomerOrderResponseDTO create(CustomerOrder customerOrder);

    AnalyticsResponseDTO create(List<CustomerOrder> customerOrders) throws ExecutionException, InterruptedException;

    CustomerOrder edit(CustomerOrder customerOrder, CustomerOrder editor);
}

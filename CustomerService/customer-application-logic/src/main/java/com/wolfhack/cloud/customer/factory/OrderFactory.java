package com.wolfhack.cloud.customer.factory;

import com.wolfhack.cloud.customer.annotations.AopLog;
import com.wolfhack.cloud.customer.bean.Factory;
import com.wolfhack.cloud.customer.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.dto.CustomerOrderResponseDTO;
import com.wolfhack.cloud.customer.factory.mapper.CustomerOrderMapper;
import com.wolfhack.cloud.customer.model.AnalyticsResponse;
import com.wolfhack.cloud.customer.model.Customer;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.model.OrderItem;
import com.wolfhack.cloud.customer.service.IOderItemService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Factory
@RequiredArgsConstructor
final class OrderFactory implements IOrderFactory, IOrderCreator {

	private final CustomerOrderMapper customerOrderMapper;
	private final IOderItemService oderItemService;

	@AopLog
	@Override
	public CustomerOrder toOrder(CustomerOrderRequestDTO requestDTO) {
		CustomerOrder customerOrder = customerOrderMapper.toOrderFromRequest(requestDTO);

		customerOrder.setCreated(LocalDateTime.now());
		Customer customer = customerOrder.getCustomer();
		customer.setEmail(customer.getEmail().toLowerCase());

		return customerOrder;
	}

	@AopLog
	@Override
	public CustomerOrderResponseDTO toResponse(CustomerOrder customerOrder) {
		return customerOrderMapper.toResponseDTO(customerOrder);
	}

	@AopLog
	@Override
	public CustomerOrder edit(CustomerOrder customerOrder, CustomerOrder editor) {
		return customerOrder.renovator().edit(editor, CustomerOrder::getCustomer, customerOrder::setCustomer).edit(editor, CustomerOrder::getAddress, customerOrder::setAddress).edit(editor, CustomerOrder::getOrderItems, customerOrder::setOrderItems).edit(editor, CustomerOrder::getDescription, customerOrder::setDescription).edit(editor, CustomerOrder::getCity, customerOrder::setCity).edit(editor, CustomerOrder::getPaymentMethod, customerOrder::setPaymentMethod).edit(editor, CustomerOrder::getPaymentCurrency, customerOrder::setPaymentCurrency).edit(editor, CustomerOrder::getState, customerOrder::setState).edit(editor, CustomerOrder::getStatus, customerOrder::setStatus).edit(editor, CustomerOrder::getZipCode, customerOrder::setZipCode).edit(editor, CustomerOrder::getCompleted, customerOrder::setCompleted).update();
	}

	@AopLog
	@Override
	public AnalyticsResponse create(List<CustomerOrder> customerOrders) throws ExecutionException, InterruptedException {
		List<OrderItem> orderItems = new ArrayList<>();
		customerOrders.stream().map(CustomerOrder::getOrderItems).forEach(orderItems::addAll);
		Callable<Long> totalQuantityCallable = () -> oderItemService.getTotalOrderQuantity(orderItems);
		Callable<Double> totalPriceCallable = () -> oderItemService.getTotalOrderPrice(orderItems);
		Callable<Double> maxPriceCallable = () -> oderItemService.getMaxPriceForItem(orderItems);
		Callable<Double> minPriceCallable = () -> oderItemService.getMinPriceForItem(orderItems);

		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future<Long> totalQuantityFuture = executor.submit(totalQuantityCallable);
		Future<Double> totalPriceFuture = executor.submit(totalPriceCallable);
		Future<Double> maxPriceFuture = executor.submit(maxPriceCallable);
		Future<Double> minPriceFuture = executor.submit(minPriceCallable);

		Double totalPrice = totalPriceFuture.get();
		Long totalQuantity = totalQuantityFuture.get();
		Double maxPrice = maxPriceFuture.get();
		Double minPrice = minPriceFuture.get();
		return new AnalyticsResponse(totalPrice, totalQuantity, maxPrice, minPrice);
	}
}

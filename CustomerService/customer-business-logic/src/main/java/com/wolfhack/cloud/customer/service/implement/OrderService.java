package com.wolfhack.cloud.customer.service.implement;

import com.wolfhack.cloud.customer.adapter.InputCustomerOrder;
import com.wolfhack.cloud.customer.adapter.NotificationSender;
import com.wolfhack.cloud.customer.adapter.OutputCustomerOrder;
import com.wolfhack.cloud.customer.adapter.PaymentAdapter;
import com.wolfhack.cloud.customer.exception.CustomerOrderNotFoundException;
import com.wolfhack.cloud.customer.factory.IOrderFactory;
import com.wolfhack.cloud.customer.model.AnalyticsResponse;
import com.wolfhack.cloud.customer.model.AnalyticsSearch;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import com.wolfhack.cloud.customer.service.IOrderService;
import com.wolfhack.cloud.customer.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

	private final IOrderFactory IOrderFactory;
	private final OutputCustomerOrder outputCustomerOrder;
	private final InputCustomerOrder inputCustomerOrder;
	private final NotificationSender notificationSender;
	private final PaymentAdapter paymentAdapter;
	private final IProductService productService;

	@Override
	public CustomerOrder save(CustomerOrder customerOrder) {
		String chargeId = paymentAdapter.pay(customerOrder);
		customerOrder.setChargeId(chargeId);
		productService.reduceProductInStorage(customerOrder.getOrderItems());

		sendCreationNotification(customerOrder);
		return inputCustomerOrder.persist(customerOrder);
	}

	@Override
	public CustomerOrder changeStatus(Long id, OrderStatus status) {
		CustomerOrder customerOrder = outputCustomerOrder.get(id)
				.map(order -> IOrderFactory.edit(order, CustomerOrder.builder()
						.status(status)
						.completed(order.isFinalDeal() ? LocalDateTime.now() : null)
						.build()))
				.orElseThrow(CustomerOrderNotFoundException::new);
		return inputCustomerOrder.persist(customerOrder);
	}

	@Override
	public CustomerOrder findById(Long id) {
		return outputCustomerOrder.get(id).orElseThrow(CustomerOrderNotFoundException::new);
	}

	@Override
	public Page<CustomerOrder> findAll(Pageable pageable) {
		return outputCustomerOrder.getAll(pageable);
	}

	@Override
	public AnalyticsResponse getAnalytics(AnalyticsSearch analyticsSearch) {
		try {
			OrderStatus status = analyticsSearch.getStatus();
			LocalDateTime from = analyticsSearch.getFrom().atTime(LocalTime.MIN);
			LocalDateTime to = analyticsSearch.getTo().atTime(LocalTime.MIN);
			List<CustomerOrder> orderItems = outputCustomerOrder.getAllByStatusAndCompletedBetween(status, from, to);
			return IOrderFactory.create(orderItems);
		} catch (ExecutionException | InterruptedException e) {
			throw new RuntimeException("The server was unable to collect analytics");
		}
	}

	private void sendCreationNotification(CustomerOrder customerOrder) {
		customerOrder.getOrderItems().forEach(notificationSender::send);
	}
}

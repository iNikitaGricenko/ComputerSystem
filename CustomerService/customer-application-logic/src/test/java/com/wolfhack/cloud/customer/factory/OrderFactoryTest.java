package com.wolfhack.cloud.customer.factory;

import com.wolfhack.cloud.customer.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.dto.CustomerOrderResponseDTO;
import com.wolfhack.cloud.customer.dto.CustomerRequestDTO;
import com.wolfhack.cloud.customer.factory.mapper.CustomerOrderMapperImpl;
import com.wolfhack.cloud.customer.model.AnalyticsResponse;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.model.OrderItem;
import com.wolfhack.cloud.customer.model.enums.Currency;
import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class OrderFactoryTest {

	private static OrderFactory orderFactory;

	@BeforeAll
	public static void setUp() {
		orderFactory = new OrderFactory(new CustomerOrderMapperImpl());
	}

	@Test
	void toOrder() {
		CustomerRequestDTO customer = new CustomerRequestDTO("email@domain.com", "Nikit", "Sambatist", "+111 (202) 555-0125");
		CustomerOrderRequestDTO requestDTO = new CustomerOrderRequestDTO("Some street 39", "description", "Mastercard", Currency.UAH, "BigCity-Town", "New-York", "USA", "96000", customer, Set.of());

		CustomerOrder order = orderFactory.toOrder(requestDTO);

		assertNotNull(order);

		assertEquals("Some street 39", order.getAddress());
		assertEquals("description", order.getDescription());
		assertEquals("Mastercard", order.getPaymentMethod());
		assertEquals(Currency.UAH, order.getPaymentCurrency());
		assertEquals("BigCity-Town", order.getCity());
		assertEquals("New-York", order.getState());
		assertEquals("USA", order.getCountry());
		assertEquals("96000", order.getZipCode());

		assertEquals("email@domain.com", order.getCustomer().getEmail());
		assertEquals("Nikit", order.getCustomer().getFirstName());
		assertEquals("Sambatist", order.getCustomer().getSecondName());
		assertEquals("+111 (202) 555-0125", order.getCustomer().getPhone());

		assertTrue(order.getOrderItems().isEmpty());
	}

	@Test
	void toResponse() {
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setPaymentMethod("Mastercard");
		customerOrder.setPaymentCurrency(Currency.UAH);
		customerOrder.setAddress("Some street 39");
		customerOrder.setCity("BigCity-Town");
		customerOrder.setState("New-York");
		customerOrder.setCountry("USA");
		customerOrder.setZipCode("96000");
		customerOrder.setStatus(OrderStatus.INPROGRESS);

		CustomerOrderResponseDTO response = orderFactory.toResponse(customerOrder);

		assertNotNull(response);

		assertEquals("Mastercard", response.getPaymentMethod());
		assertEquals(Currency.UAH, response.getPaymentCurrency());
		assertEquals("Some street 39", response.getAddress());
		assertEquals(OrderStatus.INPROGRESS, response.getStatus());
	}

	@Test
	void edit() {
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setPaymentMethod("Mastercard");
		customerOrder.setPaymentCurrency(Currency.UAH);
		customerOrder.setAddress("Some street 39");
		customerOrder.setCity("BigCity-Town");
		customerOrder.setState("New-York");
		customerOrder.setCountry("USA");
		customerOrder.setZipCode("96000");
		customerOrder.setStatus(OrderStatus.INPROGRESS);

		CustomerOrder customerOrderEditor = new CustomerOrder();
		customerOrderEditor.setPaymentCurrency(Currency.USD);
		customerOrderEditor.setStatus(OrderStatus.DELIVERED);

		orderFactory.edit(customerOrder, customerOrderEditor);

		assertNotEquals(OrderStatus.INPROGRESS, customerOrder.getStatus());
		assertNotEquals(Currency.UAH, customerOrder.getPaymentCurrency());
		assertEquals(Currency.USD, customerOrder.getPaymentCurrency());
		assertEquals(OrderStatus.DELIVERED, customerOrder.getStatus());
		assertEquals("Mastercard", customerOrder.getPaymentMethod());
		assertEquals("Some street 39", customerOrder.getAddress());
	}

	@Test
	void create() throws ExecutionException, InterruptedException {
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setPaymentMethod("Mastercard");
		customerOrder.setPaymentCurrency(Currency.UAH);
		customerOrder.setStatus(OrderStatus.DELIVERED);

		OrderItem orderItem1 = new OrderItem(null, "Name of item1", "V2", 10, 99.9f, "");
		OrderItem orderItem2 = new OrderItem(null, "Name of item2", "V0.3", 1, 0.19f, "");
		OrderItem orderItem3 = new OrderItem(null, "Name of item3", "V2", 3, 34, "");

		Set<OrderItem> orderItems = Set.of(orderItem1, orderItem2, orderItem3);
		customerOrder.setOrderItems(orderItems);

		AnalyticsResponse analyticsResponse = orderFactory.create(List.of(customerOrder));
		assertDoesNotThrow(() -> orderFactory.create(List.of(customerOrder)));
		assertNotNull(analyticsResponse);
		double totalPrice = orderItems.stream().mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).sum();
		double maxOrderPrice = orderItems.stream().mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).max().orElse(0);
		double minOrderPrice = orderItems.stream().mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).min().orElse(0);
		long totalQuantity = orderItems.stream().mapToLong(OrderItem::getQuantity).sum();

		assertEquals(minOrderPrice, analyticsResponse.getMinOrderPrice());
		assertEquals(maxOrderPrice, analyticsResponse.getMaxOrderPrice());
		assertEquals(totalPrice, analyticsResponse.getTotalPrice());
		assertEquals(totalQuantity, analyticsResponse.getTotalQuantity());
	}
}
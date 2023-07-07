package com.wolfhack.cloud.customer.service;

import com.wolfhack.cloud.customer.adapter.NotificationSender;
import com.wolfhack.cloud.customer.factory.IOrderFactory;
import com.wolfhack.cloud.customer.model.*;
import com.wolfhack.cloud.customer.model.enums.Currency;
import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import fake.adapter.FakeInputOutputCustomerOrder;
import fake.adapter.FakeNotificationSender;
import fake.factory.FakeOrderFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

	private static OrderService orderService;
	private static CustomerOrder defaultCustomerOrder;
	private static List<CustomerOrder> defaultCustomerOrders;

	@BeforeAll
	public static void setUp() {

		defaultCustomerOrder = new CustomerOrder();
		defaultCustomerOrders = new ArrayList<>();

		defaultCustomerOrder.setPaymentMethod("PrivatBank");
		defaultCustomerOrder.setPaymentCurrency(Currency.UAH);
		defaultCustomerOrder.setAddress("Some street 24");
		defaultCustomerOrder.setCity("Zaporizhzhya");
		defaultCustomerOrder.setState("Zaporizka obl");
		defaultCustomerOrder.setCountry("Ukraine");
		defaultCustomerOrder.setZipCode("69000");
		defaultCustomerOrder.setStatus(OrderStatus.INPROGRESS);

		Customer customer = new Customer();
		customer.setEmail("test@example.com");
		customer.setPhone("380123456789");
		customer.setFirstName("Nikita");
		customer.setSecondName("Hrytsenko");
		customer.setRegisterDate(LocalDateTime.now());

		defaultCustomerOrder.setCustomer(customer);

		OrderItem orderItem = new OrderItem();
		orderItem.setName("Test item");
		orderItem.setModel("Model v1");
		orderItem.setQuantity(1);
		orderItem.setUnitPrice(99.9f);
		defaultCustomerOrder.setOrderItems(Set.of(orderItem));

		defaultCustomerOrders.add(defaultCustomerOrder);

		CustomerOrder customerOrder1 = defaultCustomerOrder;
		CustomerOrder customerOrder2 = defaultCustomerOrder;

		Customer customer2 = new Customer();
		customer2.setEmail("test2@example.com");
		customer2.setPhone("380113456789");
		customer2.setFirstName("Vlad");
		customer2.setSecondName("Alchim");

		customerOrder1.setCustomer(customer2);

		Customer customer3 = new Customer();
		customer3.setEmail("test2@example.com");
		customer3.setPhone("380103456789");
		customer3.setFirstName("Dima");
		customer3.setSecondName("Micu");

		customerOrder1.setCustomer(customer3);

		defaultCustomerOrders.add(customerOrder1);
		defaultCustomerOrders.add(customerOrder2);
	}

	@BeforeEach
	public void beforeEach() {
		FakeInputOutputCustomerOrder inputOutputCustomerOrder = new FakeInputOutputCustomerOrder();
		NotificationSender notificationSender = new FakeNotificationSender();
		IOrderFactory iOrderFactory = new FakeOrderFactory();
		orderService = new OrderService(iOrderFactory, inputOutputCustomerOrder, inputOutputCustomerOrder, notificationSender);

		defaultCustomerOrder.setId(null);
		defaultCustomerOrder.setPaymentMethod("PrivatBank");
		defaultCustomerOrder.setPaymentCurrency(Currency.UAH);
		defaultCustomerOrder.setAddress("Some street 24");
		defaultCustomerOrder.setCity("Zaporizhzhya");
		defaultCustomerOrder.setState("Zaporizka obl");
		defaultCustomerOrder.setCountry("Ukraine");
		defaultCustomerOrder.setZipCode("69000");
		defaultCustomerOrder.setStatus(OrderStatus.INPROGRESS);
	}

	@Test
	void save() {
		CustomerOrder saved = orderService.save(defaultCustomerOrder);
		assertNotNull(saved);
		assertNotNull(saved.getId());
		assertNotNull(saved.getCreated());

		assertEquals(defaultCustomerOrder, saved);
	}

	@Test
	void changeStatus() {
		CustomerOrder saved = orderService.save(defaultCustomerOrder);
		CustomerOrder customerOrder = orderService.changeStatus(1L, OrderStatus.PENDING);

		assertEquals(defaultCustomerOrder.getPaymentMethod(), customerOrder.getPaymentMethod());
		assertEquals(defaultCustomerOrder.getPaymentCurrency(), customerOrder.getPaymentCurrency());
		assertEquals(defaultCustomerOrder.getAddress(), customerOrder.getAddress());
		assertEquals(defaultCustomerOrder.getCity(), customerOrder.getCity());
		assertEquals(defaultCustomerOrder.getState(), customerOrder.getState());
		assertEquals(defaultCustomerOrder.getCountry(), customerOrder.getCountry());
		assertEquals(defaultCustomerOrder.getZipCode(), customerOrder.getZipCode());

		assertNotEquals(defaultCustomerOrder.getStatus(), customerOrder.getStatus());
		assertEquals(OrderStatus.PENDING, customerOrder.getStatus());
	}

	@Test
	void findById() {
		CustomerOrder saved = orderService.save(defaultCustomerOrder);

		CustomerOrder byId = orderService.findById(1L);
		assertNotNull(byId);
		assertEquals(defaultCustomerOrder, byId);
	}

	@Test
	void getAnalytics() {
		defaultCustomerOrders.stream().peek(customerOrder -> customerOrder.setId(null)).forEach(orderService::save);

		OrderStatus status = OrderStatus.INPROGRESS;
		LocalDate from = LocalDate.now().minusDays(1);
		LocalDate to = LocalDate.now().plusDays(1);
		AnalyticsSearch analyticsSearch = new AnalyticsSearch(status, from, to);

		List<OrderItem> list = defaultCustomerOrders.stream()
				.filter(customerOrder -> customerOrder.getStatus().equals(status))
				.filter(customerOrder -> customerOrder.getCompleted() == null || customerOrder.getCompleted().isAfter(from.atTime(LocalTime.MIN)))
				.filter(customerOrder -> customerOrder.getCompleted() == null || customerOrder.getCompleted().isBefore(to.atTime(LocalTime.MIN)))
				.map(CustomerOrder::getOrderItems)
				.flatMap(Collection::stream)
				.toList();

		double totalPrice = list.stream().mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).sum();
		double maxOrderPrice = list.stream().mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).max().orElse(0);
		double minOrderPrice = list.stream().mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).min().orElse(0);
		long totalQuantity = list.stream().mapToLong(OrderItem::getQuantity).sum();

		AnalyticsResponse analytics = orderService.getAnalytics(analyticsSearch);
		assertDoesNotThrow(() -> new InterruptedException());

		assertNotNull(analytics);
		assertEquals(minOrderPrice, analytics.getMinOrderPrice());
		assertEquals(maxOrderPrice, analytics.getMaxOrderPrice());
		assertEquals(totalPrice, analytics.getTotalPrice());
		assertEquals(totalQuantity, analytics.getTotalQuantity());
	}

	@Test
	void findAll() {
		Page<CustomerOrder> all = orderService.findAll(Pageable.unpaged());

		assertNotNull(all);
		assertFalse(all.isEmpty());
		assertEquals(defaultCustomerOrders, all.getContent());
	}
}
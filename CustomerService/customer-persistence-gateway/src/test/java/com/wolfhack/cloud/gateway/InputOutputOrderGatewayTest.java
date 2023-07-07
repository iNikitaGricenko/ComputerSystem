package com.wolfhack.cloud.gateway;

import com.wolfhack.cloud.customer.adapter.InputCustomerOrder;
import com.wolfhack.cloud.customer.adapter.OutputCustomerOrder;
import com.wolfhack.cloud.customer.model.Customer;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.model.OrderItem;
import com.wolfhack.cloud.customer.model.enums.Currency;
import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import com.wolfhack.cloud.mapper.EntityCustomerOrderMapperImpl;
import fake.persistence.InMemoryOrderRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InputOutputOrderGatewayTest {

	private static InputCustomerOrder inputOrderGateway;
	private static OutputCustomerOrder outputOrderGateway;

	private static CustomerOrder defaultCustomerOrder;
	private static List<CustomerOrder> defaultCustomerOrders;

	@BeforeAll
	public static void setUp() {
		inputOrderGateway = new InputOrderGateway(new InMemoryOrderRepository(), new EntityCustomerOrderMapperImpl());
		outputOrderGateway = new OutputOrderGateway(new InMemoryOrderRepository(), new EntityCustomerOrderMapperImpl());
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

	@Test
	void persist() {
		CustomerOrder persist = inputOrderGateway.persist(defaultCustomerOrder);

		assertNotNull(persist.getId());
		assertNotNull(persist.getCustomer());
		assertNotNull(persist.getOrderItems());
		assertNotNull(persist.getCustomer().getRegisterDate());
		assertFalse(persist.getOrderItems().isEmpty());

		assertEquals(defaultCustomerOrder.getPaymentMethod(), persist.getPaymentMethod());
		assertEquals(defaultCustomerOrder.getPaymentCurrency(), persist.getPaymentCurrency());
		assertEquals(defaultCustomerOrder.getAddress(), persist.getAddress());
		assertEquals(defaultCustomerOrder.getCity(), persist.getCity());
		assertEquals(defaultCustomerOrder.getState(), persist.getState());
		assertEquals(defaultCustomerOrder.getCountry(), persist.getCountry());
		assertEquals(defaultCustomerOrder.getZipCode(), persist.getZipCode());
		assertEquals(defaultCustomerOrder.getStatus(), persist.getStatus());

		assertEquals(defaultCustomerOrder.getCustomer().getEmail(), persist.getCustomer().getEmail());
		assertEquals(defaultCustomerOrder.getCustomer().getPhone(), persist.getCustomer().getPhone());
		assertEquals(defaultCustomerOrder.getCustomer().getFirstName(), persist.getCustomer().getFirstName());
		assertEquals(defaultCustomerOrder.getCustomer().getSecondName(), persist.getCustomer().getSecondName());
	}

	@Test
	void get() {
		Optional<CustomerOrder> orderOptional = outputOrderGateway.get(1L);
		assertTrue(orderOptional.isPresent());
		CustomerOrder customerOrderFounded = orderOptional.get();

		assertNotNull(customerOrderFounded.getId());
		assertNotNull(customerOrderFounded.getCustomer());
		assertNotNull(customerOrderFounded.getOrderItems());
		assertFalse(customerOrderFounded.getOrderItems().isEmpty());

		assertEquals(defaultCustomerOrder.getPaymentMethod(), customerOrderFounded.getPaymentMethod());
		assertEquals(defaultCustomerOrder.getPaymentCurrency(), customerOrderFounded.getPaymentCurrency());
		assertEquals(defaultCustomerOrder.getAddress(), customerOrderFounded.getAddress());
		assertEquals(defaultCustomerOrder.getCity(), customerOrderFounded.getCity());
		assertEquals(defaultCustomerOrder.getState(), customerOrderFounded.getState());
		assertEquals(defaultCustomerOrder.getCountry(), customerOrderFounded.getCountry());
		assertEquals(defaultCustomerOrder.getZipCode(), customerOrderFounded.getZipCode());
		assertEquals(defaultCustomerOrder.getStatus(), customerOrderFounded.getStatus());

		assertEquals(defaultCustomerOrder.getCustomer().getEmail(), customerOrderFounded.getCustomer().getEmail());
		assertEquals(defaultCustomerOrder.getCustomer().getPhone(), customerOrderFounded.getCustomer().getPhone());
		assertEquals(defaultCustomerOrder.getCustomer().getFirstName(), customerOrderFounded.getCustomer().getFirstName());
		assertEquals(defaultCustomerOrder.getCustomer().getSecondName(), customerOrderFounded.getCustomer().getSecondName());
		assertEquals(defaultCustomerOrder.getCustomer().getRegisterDate(), customerOrderFounded.getCustomer().getRegisterDate());
	}

	@Test
	void getAll() {
		defaultCustomerOrders.forEach(inputOrderGateway::persist);

		List<CustomerOrder> content = outputOrderGateway.getAll(Pageable.unpaged()).getContent();

		assertFalse(content.isEmpty());
		assertTrue(content.containsAll(defaultCustomerOrders));
	}

	@Test
	void getAllByDeliveredStatusAndCompletedBetweenWhenCompletedIsNull() {
		defaultCustomerOrders.forEach(inputOrderGateway::persist);

		OrderStatus status = OrderStatus.DELIVERED;
		LocalDateTime from = LocalDateTime.now().minusDays(1);
		LocalDateTime to = LocalDateTime.now().plusDays(1);

		List<CustomerOrder> customerOrders = outputOrderGateway.getAllByStatusAndCompletedBetween(status, from, to);

		assertTrue(customerOrders.isEmpty());

		List<CustomerOrder> orders = defaultCustomerOrders.stream()
				.filter(customerOrder -> customerOrder.getStatus().equals(status))
				.filter(customerOrder -> customerOrder.getCompleted() != null)
				.filter(customerOrder -> customerOrder.getCompleted().isAfter(from))
				.filter(customerOrder -> customerOrder.getCompleted().isBefore(to))
				.toList();

		assertTrue(orders.isEmpty());
		assertEquals(orders, customerOrders);
	}

	@Test
	void getAllByInProgressStatusAndCompletedBetweenWhenCompletedIsNull() {
		defaultCustomerOrders.forEach(inputOrderGateway::persist);

		OrderStatus status = OrderStatus.INPROGRESS;
		LocalDateTime from = LocalDateTime.now().minusDays(1);
		LocalDateTime to = LocalDateTime.now().plusDays(1);

		List<CustomerOrder> customerOrders = outputOrderGateway.getAllByStatusAndCompletedBetween(status, from, to);

		assertFalse(customerOrders.isEmpty());

		List<CustomerOrder> orders = defaultCustomerOrders.stream()
				.filter(customerOrder -> customerOrder.getStatus().equals(status))
				.filter(customerOrder -> customerOrder.getCompleted() == null || customerOrder.getCompleted().isAfter(from))
				.filter(customerOrder -> customerOrder.getCompleted() == null || customerOrder.getCompleted().isBefore(to))
				.toList();

		assertFalse(orders.isEmpty());
		assertEquals(orders, customerOrders);
	}
}
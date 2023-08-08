package com.wolfhack.cloud.customer.service;

import com.wolfhack.cloud.customer.model.Customer;
import com.wolfhack.cloud.customer.service.implement.CustomerService;
import fake.adapter.FakeInputOutputCustomer;
import fake.adapter.FakeStripeCustomerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class CustomerServiceTest {

	private static CustomerService customerService;
	private static Customer defaultCustomer;

	@BeforeAll
	public static void setUp() {
		FakeInputOutputCustomer inputOutputCustomer = new FakeInputOutputCustomer();
		IStripeCustomerService stripeCustomerService = new FakeStripeCustomerService();
		customerService = new CustomerService(inputOutputCustomer, inputOutputCustomer, stripeCustomerService);
		defaultCustomer = new Customer();

		defaultCustomer.setEmail("test@example.com");
		defaultCustomer.setPhone("380123456789");
		defaultCustomer.setFirstName("Nikita");
		defaultCustomer.setSecondName("Hrytsenko");
	}

	@Test
	void save() {
		Customer saved = customerService.save(defaultCustomer);
		assertEquals(defaultCustomer, saved);
	}

	@Test
	void findById() {
		Customer founded = customerService.findById(1L);

		assertEquals(defaultCustomer, founded);
	}

	@Test
	void findAll() {
		ArrayList<Customer> customers = new ArrayList<>();

		customers.add(defaultCustomer);

		Customer customer2 = new Customer();
		customer2.setEmail("test2@example.com");
		customer2.setPhone("380113456789");
		customer2.setFirstName("Vlad");
		customer2.setSecondName("Alchim");
		customers.add(customer2);

		Customer customer3 = new Customer();
		customer3.setEmail("test3@example.com");
		customer3.setPhone("380103456789");
		customer3.setFirstName("Dima");
		customer3.setSecondName("Micu");
		customers.add(customer3);

		customers.forEach(customerService::save);

		assertIterableEquals(customers, customerService.findAll(Pageable.ofSize(30)));
	}
}
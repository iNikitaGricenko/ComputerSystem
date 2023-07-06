package com.wolfhack.cloud.customer.service;

import com.wolfhack.cloud.customer.model.Customer;
import fake.adapter.FakeInputCustomer;
import fake.adapter.FakeOutputCustomer;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class CustomerServiceTest {

	@Test
	void save() {
		CustomerService customerService = new CustomerService(new FakeInputCustomer(), new FakeOutputCustomer());

		Customer customer = new Customer();
		customer.setEmail("test@example.com");
		customer.setPhone("380123456789");
		customer.setFirstName("Nikita");
		customer.setSecondName("Hrytsenko");
		customer.setRegisterDate(LocalDateTime.now());

		assertEquals(customer, customerService.save(customer));
	}

	@Test
	void findById() {
		CustomerService customerService = new CustomerService(new FakeInputCustomer(), new FakeOutputCustomer());

		Customer customer = new Customer();
		customer.setEmail("test@example.com");
		customer.setPhone("380123456789");
		customer.setFirstName("Nikita");
		customer.setSecondName("Hrytsenko");
		customer.setRegisterDate(LocalDateTime.now());

		Customer saved = customerService.save(customer);

		assertEquals(customer, customerService.findById(saved.getId()));
	}

	@Test
	void findAll() {
		CustomerService customerService = new CustomerService(new FakeInputCustomer(), new FakeOutputCustomer());
		ArrayList<Customer> customers = new ArrayList<>();

		Customer customer1 = new Customer();
		customer1.setEmail("test@example.com");
		customer1.setPhone("380123456789");
		customer1.setFirstName("Nikita");
		customer1.setSecondName("Hrytsenko");
		customer1.setRegisterDate(LocalDateTime.now());
		customers.add(customer1);

		Customer customer2 = new Customer();
		customer2.setEmail("test2@example.com");
		customer2.setPhone("380113456789");
		customer2.setFirstName("Vlad");
		customer2.setSecondName("Alchim");
		customer1.setRegisterDate(LocalDateTime.now());
		customers.add(customer2);

		Customer customer3 = new Customer();
		customer3.setEmail("test2@example.com");
		customer3.setPhone("380103456789");
		customer3.setFirstName("Dima");
		customer3.setSecondName("Micu");
		customer1.setRegisterDate(LocalDateTime.now());
		customers.add(customer3);

		customers.forEach(customerService::save);

		assertIterableEquals(customers, customerService.findAll(Pageable.ofSize(30)));
	}
}
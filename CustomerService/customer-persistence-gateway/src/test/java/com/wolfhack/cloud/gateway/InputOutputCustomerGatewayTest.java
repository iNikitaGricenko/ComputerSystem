package com.wolfhack.cloud.gateway;

import com.wolfhack.cloud.customer.adapter.InputCustomer;
import com.wolfhack.cloud.customer.adapter.OutputCustomer;
import com.wolfhack.cloud.customer.model.Customer;
import com.wolfhack.cloud.mapper.EntityCustomerMapperImpl;
import fake.persistence.InMemoryCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InputOutputCustomerGatewayTest {

	private static InputCustomer inputCustomerGateway;
	private static OutputCustomer outputCustomerGateway;

	private static Customer defaultCustomer;
	private static List<Customer> defaultCustomers;

	@BeforeEach
	public void setUp() {
		inputCustomerGateway = new InputCustomerGateway(new InMemoryCustomerRepository(), new EntityCustomerMapperImpl());
		outputCustomerGateway = new OutputCustomerGateway(new InMemoryCustomerRepository(), new EntityCustomerMapperImpl());
		defaultCustomer = new Customer();
		defaultCustomers = new ArrayList<>();

		defaultCustomer.setEmail("test@example.com");
		defaultCustomer.setPhone("380123456789");
		defaultCustomer.setFirstName("Nikita");
		defaultCustomer.setSecondName("Hrytsenko");

		defaultCustomers.add(defaultCustomer);

		Customer customer2 = new Customer();
		customer2.setEmail("test2@example.com");
		customer2.setPhone("380113456789");
		customer2.setFirstName("Vlad");
		customer2.setSecondName("Alchim");
		defaultCustomers.add(customer2);

		Customer customer3 = new Customer();
		customer3.setEmail("test2@example.com");
		customer3.setPhone("380103456789");
		customer3.setFirstName("Dima");
		customer3.setSecondName("Micu");
		defaultCustomers.add(customer3);
	}

	@Test
	void persist() {
		Customer persist = inputCustomerGateway.persist(defaultCustomer);

		assertNotNull(persist.getId());
		assertEquals(defaultCustomer.getEmail(), persist.getEmail());
		assertEquals(defaultCustomer.getPhone(), persist.getPhone());
		assertEquals(defaultCustomer.getFirstName(), persist.getFirstName());
		assertEquals(defaultCustomer.getSecondName(), persist.getSecondName());
		assertEquals(defaultCustomer.getRegisterDate(), persist.getRegisterDate());
	}

	@Test
	void get() {
		Optional<Customer> customerOptional = outputCustomerGateway.get(1L);
		assertTrue(customerOptional.isPresent());
		Customer customerFounded = customerOptional.get();

		assertNotNull(customerFounded.getId());
		assertNotNull(customerFounded.getRegisterDate());

		assertEquals(defaultCustomer.getEmail(), customerFounded.getEmail());
		assertEquals(defaultCustomer.getPhone(), customerFounded.getPhone());
		assertEquals(defaultCustomer.getFirstName(), customerFounded.getFirstName());
		assertEquals(defaultCustomer.getSecondName(), customerFounded.getSecondName());
	}

	@Test
	void getAll() {
		defaultCustomers.forEach(inputCustomerGateway::persist);

		List<Customer> content = outputCustomerGateway.getAll(Pageable.unpaged()).getContent();

		assertFalse(content.isEmpty());
		assertEquals(content, defaultCustomers);
	}
}
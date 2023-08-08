package com.wolfhack.cloud.customer.service;

import com.stripe.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStripeCustomerService {
	Customer addCustomer(com.wolfhack.cloud.customer.model.Customer customer);

	void deleteCustomer(String customerId);

	Page<Customer> retrieveAll(Pageable pageable);

	Customer retrieve(String customerId);

	Customer retrieveWithSources(String customer);
}

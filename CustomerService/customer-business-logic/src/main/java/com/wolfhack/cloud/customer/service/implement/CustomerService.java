package com.wolfhack.cloud.customer.service.implement;

import com.wolfhack.cloud.customer.adapter.InputCustomer;
import com.wolfhack.cloud.customer.adapter.OutputCustomer;
import com.wolfhack.cloud.customer.exception.CustomerNotFoundException;
import com.wolfhack.cloud.customer.model.Customer;
import com.wolfhack.cloud.customer.service.ICustomerService;
import com.wolfhack.cloud.customer.service.IStripeCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

	private final InputCustomer inputCustomer;
	private final OutputCustomer outputCustomer;
	private final IStripeCustomerService stripeCustomerService;

	@Override
	public Customer save(Customer customer) {
		String reference = stripeCustomerService.addCustomer(customer).getId();
		customer.setStripeReference(reference);
		customer.setRegisterDate(LocalDateTime.now());
		return inputCustomer.persist(customer);
	}

	@Override
	public Customer findById(Long id) {
		return outputCustomer.get(id).orElseThrow(CustomerNotFoundException::new);
	}

	@Override
	public Page<Customer> findAll(Pageable pageable) {
		return outputCustomer.getAll(pageable);
	}

	@Override
	public Customer update(Long id, Customer customer) {
		return inputCustomer.update(id, customer);
	}

	@Override
	public Customer findByEmail(String email) {
		return outputCustomer.get(email);
	}
}

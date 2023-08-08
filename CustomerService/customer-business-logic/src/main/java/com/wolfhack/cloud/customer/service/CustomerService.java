package com.wolfhack.cloud.customer.service;

import com.wolfhack.cloud.customer.adapter.InputCustomer;
import com.wolfhack.cloud.customer.adapter.OutputCustomer;
import com.wolfhack.cloud.customer.exception.CustomerNotFoundException;
import com.wolfhack.cloud.customer.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

	private final InputCustomer inputCustomer;
	private final OutputCustomer outputCustomer;

	@Override
	public Customer save(Customer customer) {
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
}

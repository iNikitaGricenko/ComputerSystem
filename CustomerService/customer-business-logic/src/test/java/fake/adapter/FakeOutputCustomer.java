package fake.adapter;

import com.wolfhack.cloud.customer.adapter.OutputCustomer;
import com.wolfhack.cloud.customer.model.Customer;
import fake.persistence.persistence.InMemoryCustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

public class FakeOutputCustomer implements OutputCustomer {

	private final InMemoryCustomerRepository repository = new InMemoryCustomerRepository();

	@Override
	public Optional<Customer> get(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<Customer> getAll(Pageable pageable) {
		List<Customer> customers = new ArrayList<>();
		repository.findAll().forEach(customers::add);
		return new PageImpl<>(customers, pageable, customers.size());
	}
}

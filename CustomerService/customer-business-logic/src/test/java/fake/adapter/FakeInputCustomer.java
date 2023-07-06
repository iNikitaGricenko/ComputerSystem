package fake.adapter;

import com.wolfhack.cloud.customer.adapter.InputCustomer;
import com.wolfhack.cloud.customer.model.Customer;
import fake.persistence.persistence.InMemoryCustomerRepository;

public class FakeInputCustomer implements InputCustomer {

	private final InMemoryCustomerRepository repository = new InMemoryCustomerRepository();

	@Override
	public Customer persist(Customer customerOrder) {
		return repository.save(customerOrder);
	}
}

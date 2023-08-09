package fake.adapter;

import com.stripe.model.Customer;
import com.wolfhack.cloud.customer.service.IStripeCustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

public class FakeStripeCustomerService implements IStripeCustomerService {

	private static final Map<String, Customer> fakeTable = new HashMap<String, Customer>();

	@Override
	public Customer addCustomer(com.wolfhack.cloud.customer.model.Customer customer) {
		String id = null;
		Customer customer1 = new Customer();
		customer1.setId(id);
		fakeTable.put(id, customer1);
		return customer1;
	}

	@Override
	public void deleteCustomer(String customerId) {
		fakeTable.remove(customerId);
	}

	@Override
	public Page<Customer> retrieveAll(Pageable pageable) {
		return Page.empty();
	}

	@Override
	public Customer retrieve(String customerId) {
		return fakeTable.get(customerId);
	}

	@Override
	public Customer retrieveWithSources(String customer) {
		return fakeTable.get(customer);
	}
}

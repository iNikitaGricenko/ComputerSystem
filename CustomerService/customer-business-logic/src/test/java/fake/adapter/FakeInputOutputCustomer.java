package fake.adapter;

import com.wolfhack.cloud.customer.adapter.InputCustomer;
import com.wolfhack.cloud.customer.adapter.OutputCustomer;
import com.wolfhack.cloud.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

public class FakeInputOutputCustomer implements InputCustomer, OutputCustomer {

	private static final Map<Long, Customer> fakeTable = new HashMap<Long, Customer>();
	private static long id = 0;

	@Override
	public Customer persist(Customer customer) {
		Customer customerOrderNew = new Customer();
		if (customer.getId() == null) {

			customerOrderNew.setEmail(customer.getEmail());
			customerOrderNew.setPhone(customer.getPhone());
			customerOrderNew.setFirstName(customer.getFirstName());
			customerOrderNew.setSecondName(customer.getSecondName());

			customerOrderNew.setId(++id);
			fakeTable.put(id, customerOrderNew);
		} else {
			customerOrderNew.setId(customer.getId());
			customerOrderNew.setEmail(customer.getEmail());
			customerOrderNew.setPhone(customer.getPhone());
			customerOrderNew.setFirstName(customer.getFirstName());
			customerOrderNew.setSecondName(customer.getSecondName());
			fakeTable.put(customer.getId(), customerOrderNew);
		}
		return customerOrderNew;
	}

	@Override
	public Customer update(Long id, Customer customer) {
		return fakeTable.get(id).renovator().update();
	}

	@Override
	public Optional<Customer> get(Long id) {
		return Optional.ofNullable(fakeTable.get(id));
	}

	@Override
	public Page<Customer> getAll(Pageable pageable) {
		List<Customer> customers = new ArrayList<>(fakeTable.values());
		return new PageImpl<>(customers, pageable, customers.size());
	}

	@Override
	public Customer get(String email) {
		return fakeTable.values().stream().filter(customer -> customer.getEmail().equalsIgnoreCase(email)).findFirst().orElseThrow();
	}
}

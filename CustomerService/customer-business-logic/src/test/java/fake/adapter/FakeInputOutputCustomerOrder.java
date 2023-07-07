package fake.adapter;

import com.wolfhack.cloud.customer.adapter.InputCustomerOrder;
import com.wolfhack.cloud.customer.adapter.OutputCustomerOrder;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.*;

public class FakeInputOutputCustomerOrder implements InputCustomerOrder, OutputCustomerOrder {

	private static final Map<Long, CustomerOrder> fakeTable = new HashMap<Long, CustomerOrder>();
	private static long id = 0;

	@Override
	public CustomerOrder persist(CustomerOrder customerOrder) {
		if (customerOrder.getId() == null) {
			CustomerOrder customerOrderNew = new CustomerOrder();

			customerOrderNew.setPaymentMethod(customerOrder.getPaymentMethod());
			customerOrderNew.setPaymentCurrency(customerOrder.getPaymentCurrency());
			customerOrderNew.setAddress(customerOrder.getAddress());
			customerOrderNew.setCity(customerOrder.getCity());
			customerOrderNew.setState(customerOrder.getState());
			customerOrderNew.setCountry(customerOrder.getCountry());
			customerOrderNew.setZipCode(customerOrder.getZipCode());
			customerOrderNew.setStatus(customerOrder.getStatus());
			customerOrderNew.setCustomer(customerOrder.getCustomer());
			customerOrderNew.setOrderItems(customerOrder.getOrderItems());

			customerOrderNew.setId(++id);
			fakeTable.put(id, customerOrderNew);
			return customerOrderNew;
		} else {
			CustomerOrder customerOrderNew = new CustomerOrder();
			customerOrderNew.setId(customerOrder.getId());
			customerOrderNew.setPaymentMethod(customerOrder.getPaymentMethod());
			customerOrderNew.setPaymentCurrency(customerOrder.getPaymentCurrency());
			customerOrderNew.setAddress(customerOrder.getAddress());
			customerOrderNew.setCity(customerOrder.getCity());
			customerOrderNew.setState(customerOrder.getState());
			customerOrderNew.setCountry(customerOrder.getCountry());
			customerOrderNew.setZipCode(customerOrder.getZipCode());
			customerOrderNew.setStatus(customerOrder.getStatus());
			customerOrderNew.setCustomer(customerOrder.getCustomer());
			customerOrderNew.setOrderItems(customerOrder.getOrderItems());
			fakeTable.put(customerOrder.getId(), customerOrderNew);
			return customerOrderNew;
		}
	}

	@Override
	public Optional<CustomerOrder> get(Long id) {
		return Optional.ofNullable(fakeTable.get(id));
	}

	@Override
	public List<CustomerOrder> getAllByStatusAndCompletedBetween(OrderStatus status, LocalDateTime from, LocalDateTime to) {
		return fakeTable.values().stream()
				.filter(customerOrder -> customerOrder.getStatus().equals(status))
				.filter(customerOrder -> customerOrder.getCompleted() == null || customerOrder.getCompleted().isAfter(from))
				.filter(customerOrder -> customerOrder.getCompleted() == null || customerOrder.getCompleted().isBefore(to))
				.toList();
	}

	@Override
	public Page<CustomerOrder> getAll(Pageable pageable) {
		List<CustomerOrder> customerOrders = new ArrayList<>(fakeTable.values());
		return new PageImpl<>(customerOrders, pageable, customerOrders.size());
	}
}

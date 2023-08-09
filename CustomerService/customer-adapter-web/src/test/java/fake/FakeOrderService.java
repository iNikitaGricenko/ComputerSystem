package fake;

import com.wolfhack.cloud.customer.model.AnalyticsResponse;
import com.wolfhack.cloud.customer.model.AnalyticsSearch;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import com.wolfhack.cloud.customer.service.IOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeOrderService implements IOrderService {

	private static final Map<Long, CustomerOrder> fakeTable = new HashMap<Long, CustomerOrder>();
	private static long id = 0;

	@Override
	public CustomerOrder save(CustomerOrder customerOrder) {
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
		if (customerOrder.getId() == null) {
			customerOrderNew.setId(++id);
			fakeTable.put(id, customerOrderNew);
		} else {
			customerOrderNew.setId(customerOrder.getId());
			fakeTable.put(customerOrder.getId(), customerOrderNew);
		}
		return customerOrderNew;
	}

	@Override
	public CustomerOrder changeStatus(Long id, OrderStatus status) {
		CustomerOrder customerOrder = fakeTable.get(id);
		customerOrder.setStatus(status);
		fakeTable.put(id, customerOrder);
		return customerOrder;
	}

	@Override
	public CustomerOrder findById(Long id) {
		return fakeTable.get(id);
	}

	@Override
	public Page<CustomerOrder> findAll(Pageable pageable) {
		List<CustomerOrder> customerOrders = new ArrayList<>(fakeTable.values());
		return new PageImpl<>(customerOrders, pageable, customerOrders.size());
	}

	@Override
	public AnalyticsResponse getAnalytics(AnalyticsSearch analyticsSearch) {
		int totalPrice = 3475;
		int totalQuantity = 2;
		int maxOrderPrice = 3405;
		int minOrderPrice = 70;
		return new AnalyticsResponse(totalPrice, totalQuantity, maxOrderPrice, minOrderPrice);
	}
}

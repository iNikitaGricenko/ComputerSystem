package fake.factory;

import com.wolfhack.cloud.customer.factory.IOrderFactory;
import com.wolfhack.cloud.customer.model.AnalyticsResponse;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.model.OrderItem;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FakeOrderFactory implements IOrderFactory {
	@Override
	public AnalyticsResponse create(List<CustomerOrder> customerOrders) throws ExecutionException, InterruptedException {
		List<OrderItem> orderItems = customerOrders.stream().map(CustomerOrder::getOrderItems).flatMap(Collection::stream).toList();

		double totalPrice = orderItems.stream().mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).sum();
		double maxPricePerOrder = orderItems.stream().mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).max().orElse(0);
		double minPricePerOrder = orderItems.stream().mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).min().orElse(0);
		long totalQuantity = orderItems.stream().mapToLong(OrderItem::getQuantity).sum();
		return new AnalyticsResponse(totalPrice, totalQuantity, maxPricePerOrder, minPricePerOrder);
	}

	@Override
	public CustomerOrder edit(CustomerOrder customerOrder, CustomerOrder editor) {
		return customerOrder.renovator()
				.edit(editor, CustomerOrder::getCustomer, customerOrder::setCustomer)
				.edit(editor, CustomerOrder::getAddress, customerOrder::setAddress)
				.edit(editor, CustomerOrder::getOrderItems, customerOrder::setOrderItems)
				.edit(editor, CustomerOrder::getDescription, customerOrder::setDescription)
				.edit(editor, CustomerOrder::getCity, customerOrder::setCity)
				.edit(editor, CustomerOrder::getPaymentMethod, customerOrder::setPaymentMethod)
				.edit(editor, CustomerOrder::getPaymentCurrency, customerOrder::setPaymentCurrency)
				.edit(editor, CustomerOrder::getState, customerOrder::setState)
				.edit(editor, CustomerOrder::getStatus, customerOrder::setStatus)
				.edit(editor, CustomerOrder::getZipCode, customerOrder::setZipCode)
				.edit(editor, CustomerOrder::getCompleted, customerOrder::setCompleted)
				.update();
	}
}

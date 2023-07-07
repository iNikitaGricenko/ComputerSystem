package fake.factory;

import com.wolfhack.cloud.customer.factory.IOrderFactory;
import com.wolfhack.cloud.customer.model.AnalyticsResponse;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.model.OrderItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.*;

public class FakeOrderFactory implements IOrderFactory {
	@Override
	public AnalyticsResponse create(List<CustomerOrder> customerOrders) throws ExecutionException, InterruptedException {
		List<OrderItem> orderItems = customerOrders.stream().map(CustomerOrder::getOrderItems).flatMap(Collection::stream).toList();

		DoubleSummaryStatistics doubleSummaryStatistics = orderItems.stream().mapToDouble(OrderItem::getUnitPrice).summaryStatistics();
		long totalQuantity = orderItems.stream().mapToLong(OrderItem::getQuantity).sum();

		double totalPrice = doubleSummaryStatistics.getSum();
		double maxPrice = doubleSummaryStatistics.getMax();
		double minPrice = doubleSummaryStatistics.getMin();
		return new AnalyticsResponse(totalPrice, totalQuantity, maxPrice, minPrice);
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

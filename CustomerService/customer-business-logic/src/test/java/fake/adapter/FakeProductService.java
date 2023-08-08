package fake.adapter;

import com.wolfhack.cloud.customer.model.OrderItem;
import com.wolfhack.cloud.customer.service.IProductService;

import java.util.Collection;

public class FakeProductService implements IProductService {
	@Override
	public void reduceProductInStorage(Collection<OrderItem> orderItems) {

	}
}

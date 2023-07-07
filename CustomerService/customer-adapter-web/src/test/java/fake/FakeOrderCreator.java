package fake;

import com.wolfhack.cloud.customer.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.dto.CustomerOrderResponseDTO;
import com.wolfhack.cloud.customer.factory.IOrderCreator;
import com.wolfhack.cloud.customer.factory.mapper.CustomerOrderMapper;
import com.wolfhack.cloud.customer.factory.mapper.CustomerOrderMapperImpl;
import com.wolfhack.cloud.customer.model.CustomerOrder;

public class FakeOrderCreator implements IOrderCreator {

	private final CustomerOrderMapper customerOrderMapper = new CustomerOrderMapperImpl();

	@Override
	public CustomerOrder toOrder(CustomerOrderRequestDTO requestDTO) {
		return customerOrderMapper.toOrderFromRequest(requestDTO);
	}

	@Override
	public CustomerOrderResponseDTO toResponse(CustomerOrder customerOrder) {
		return customerOrderMapper.toResponseDTO(customerOrder);
	}
}

package fake.mapper;

import com.wolfhack.cloud.customer.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.dto.CustomerOrderResponseDTO;
import com.wolfhack.cloud.customer.factory.mapper.CustomerOrderMapper;
import com.wolfhack.cloud.customer.model.CustomerOrder;

public class FakeCustomerOrderMapper implements CustomerOrderMapper {
	@Override
	public CustomerOrder toOrderFromRequest(CustomerOrderRequestDTO customerOrderRequestDTO) {
		return null;
	}

	@Override
	public CustomerOrderRequestDTO toRequestDTO(CustomerOrder customerOrder) {
		return null;
	}

	@Override
	public CustomerOrder toOrderFromResponse(CustomerOrderResponseDTO customerOrderResponseDTO) {
		return null;
	}

	@Override
	public CustomerOrderResponseDTO toResponseDTO(CustomerOrder customerOrder) {
		return null;
	}
}

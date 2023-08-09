package com.wolfhack.cloud.gateway;

import com.wolfhack.cloud.customer.adapter.InputCustomerOrder;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.entity.EntityCustomerOrder;
import com.wolfhack.cloud.mapper.EntityCustomerOrderMapper;
import com.wolfhack.cloud.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InputOrderGateway implements InputCustomerOrder {

	private final OrderRepository orderRepository;
	private final EntityCustomerOrderMapper orderMapper;

	@Override
	public CustomerOrder persist(CustomerOrder customerOrder) {
		EntityCustomerOrder entityCustomerOrder = orderMapper.toEntityFromBusiness(customerOrder);
		return orderMapper.toBusinessFromEntity(orderRepository.save(entityCustomerOrder));
	}
}

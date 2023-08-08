package com.wolfhack.cloud.customer.client;

import com.wolfhack.cloud.customer.model.OrderItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@FeignClient("product-service")
public interface ProductClient {

	@RequestMapping(value = "/api/product", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	void removeProducts(@RequestBody Collection<Long> productIds);

	@RequestMapping(value = "/api/product/validate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	void validateProducts(@RequestBody Collection<OrderItem> products);

}

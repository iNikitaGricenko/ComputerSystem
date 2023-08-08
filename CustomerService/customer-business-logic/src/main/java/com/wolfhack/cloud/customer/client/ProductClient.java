package com.wolfhack.cloud.customer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@FeignClient("product-service")
public interface ProductClient {

	@RequestMapping(value = "/api/product/remove", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	void removeProducts(Collection<Long> productIds);

}

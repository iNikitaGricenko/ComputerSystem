package com.wolfhack.cloud.customer.client;

import com.wolfhack.cloud.customer.model.OrderItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@FeignClient("email-service")
public interface EmailSenderClient {

	@RequestMapping(
			value = "/api/email/send/charge/{receiver}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	void sendFailedEmail(@RequestBody Collection<OrderItem> productIds, @PathVariable String receiver);

	@RequestMapping(
			value = "/api/email/failed/send/charge/{receiver}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	void sendSuccessEmail(@RequestBody Collection<OrderItem> products, @PathVariable String receiver);

}

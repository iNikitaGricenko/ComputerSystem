package com.wolfhack.cloud.business.client;

import com.wolfhack.cloud.business.dto.UserEmailData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "email-service")
public interface EmailClient {

	@RequestMapping(value = "/activate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	void send(UserEmailData userEmailData);

}

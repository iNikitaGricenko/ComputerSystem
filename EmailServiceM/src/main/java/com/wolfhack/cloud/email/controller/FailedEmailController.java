package com.wolfhack.cloud.email.controller;

import com.wolfhack.cloud.email.model.Order;
import com.wolfhack.cloud.email.model.OrderMailDetails;
import com.wolfhack.cloud.email.service.ChargeFailedEmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/api/email/failed/send")
@RequiredArgsConstructor
public class FailedEmailController {

	private final ChargeFailedEmailSender chargeFailedEmailSender;

	@PostMapping("/charge/{receiver}")
	public Mono<Void> sendFailedCharge(@RequestBody Mono<List<Order>> target, @PathVariable String receiver) throws MessagingException {
		String subject = "Activate your account";
		return target
				.map(orders -> new OrderMailDetails(receiver, orders))
				.doOnNext(orderMailDetails -> chargeFailedEmailSender.send(orderMailDetails, subject))
				.then();
	}

}

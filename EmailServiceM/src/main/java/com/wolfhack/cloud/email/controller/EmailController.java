package com.wolfhack.cloud.email.controller;

import com.wolfhack.cloud.email.model.Order;
import com.wolfhack.cloud.email.model.OrderMailDetails;
import com.wolfhack.cloud.email.model.User;
import com.wolfhack.cloud.email.service.ChargeSuccessEmailSender;
import com.wolfhack.cloud.email.service.UserActivationEmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/api/email/send")
@RequiredArgsConstructor
public class EmailController {

	private final UserActivationEmailSender userActivationEmailSender;
	private final ChargeSuccessEmailSender chargeSuccessEmailSender;

	@PostMapping("/activate")
	public Mono<Void> sendActivationEmail(@RequestBody Mono<User> target) throws MessagingException {
		String subject = "Activate your account";
		return target.doOnNext(user -> userActivationEmailSender.send(user, subject)).then();
	}

	@PostMapping("/charge/{receiver}")
	public Mono<Void> sendSuccessCharge(@RequestBody Mono<List<Order>> target, @PathVariable String receiver) throws MessagingException {
		String subject = "Success charge";
		return target
				.map(orders -> new OrderMailDetails(receiver, orders))
				.doOnNext(orderMailDetails -> chargeSuccessEmailSender.send(orderMailDetails, subject))
				.then();
	}

}

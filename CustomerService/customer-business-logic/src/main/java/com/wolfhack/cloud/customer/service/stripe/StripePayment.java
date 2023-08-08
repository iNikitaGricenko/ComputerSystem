package com.wolfhack.cloud.customer.service.stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.wolfhack.cloud.customer.adapter.PaymentAdapter;
import com.wolfhack.cloud.customer.model.Customer;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.service.ICustomerService;
import com.wolfhack.cloud.customer.utils.StripeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripePayment implements PaymentAdapter {

	private final StripeUtils stripeUtils;
	private final StripePaymentMethodService stripePaymentMethodService;
	private final ICustomerService customerService;
	private final StripeCustomerService stripeCustomerService;
	private final StripeCardService stripeCardService;

	@Override
	public String pay(CustomerOrder order) {
		Customer entityCustomer = Optional.ofNullable(order.getCustomer().getId()).map(customerService::findById).orElseGet(() -> customerService.findByEmail(order.getCustomer().getEmail()));
		String customerReference = entityCustomer.getStripeReference();

		try {
			order.setCustomer(entityCustomer);
			createPayment(order, entityCustomer, customerReference);

			return Charge.create(stripeUtils.exctractChargeCreateParams(order, customerReference)).getId();
		} catch (StripeException e) {
			String email = entityCustomer.getEmail();
			// TODO send failed email to user
			throw new RuntimeException(e);
		}
	}

	@Override
	public String confirm(String chargeReference) {
		try {
			Charge charge = Charge.retrieve(chargeReference);
			charge.capture();
			return charge.getId();
		} catch (StripeException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String cancel(String chargeReference, Long refundedAmount) {
		try {
			Charge charge = Charge.retrieve(chargeReference);
			charge.setRefunded(true);
			charge.setAmountRefunded(refundedAmount);
			return charge.getId();
		} catch (StripeException e) {
			throw new RuntimeException(e);
		}
	}

	private void createPayment(CustomerOrder order, Customer entityCustomer, String customerReference) {
		if (StringUtils.isEmpty((entityCustomer.getCard()))) {
			stripePaymentMethodService.create(order, customerReference);

			com.stripe.model.Customer withSources = stripeCustomerService.retrieveWithSources(customerReference);
			String cardReference = stripeCardService.addCustomerCard(withSources, order.getPaymentMethod());

			entityCustomer.setCard(cardReference);
			customerService.update(entityCustomer.getId(), entityCustomer);
		}
	}

}

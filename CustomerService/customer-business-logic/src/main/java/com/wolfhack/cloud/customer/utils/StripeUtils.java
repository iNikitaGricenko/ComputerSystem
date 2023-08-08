package com.wolfhack.cloud.customer.utils;

import com.neovisionaries.i18n.CountryCode;
import com.stripe.param.ChargeCreateParams;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.service.IOderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StripeUtils {

	private final IOderItemService oderItemService;

	public ChargeCreateParams exctractChargeCreateParams(CustomerOrder order, String customer) {
		return ChargeCreateParams.builder().setAmount((long) oderItemService.getTotalOrderPrice(order.getOrderItems())).setCurrency(order.getPaymentCurrency().getAbbreviation()).setDescription(order.getDescription()).setCustomer(customer).setReceiptEmail(order.getCustomer().getEmail()).setShipping(exctractShipping(order)).setCapture(false).build();
	}

	public ChargeCreateParams.Shipping exctractShipping(CustomerOrder order) {
		return ChargeCreateParams.Shipping.builder().setAddress(exctractAddress(order)).setName(order.getCustomer().getFullName()).setPhone(order.getCustomer().getPhone()).build();
	}

	public ChargeCreateParams.Shipping.Address exctractAddress(CustomerOrder order) {
		String countryCode = CountryCode.findByName(order.getCountry()).stream().findFirst().map(CountryCode::getName).orElse("");

		return ChargeCreateParams.Shipping.Address.builder().setCity(order.getCity()).setCountry(countryCode).setPostalCode(order.getZipCode()).setState(order.getState()).build();
	}

}

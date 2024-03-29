package com.wolfhack.cloud.customer.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currency {
	USD("Dollar", "USD", "$"), UAH("Hryvnia", "UAH", "₴"), EUR("Euro", "EUR", "€"), GBP("Pound Sterling", "GBP", "£"), JPY("Yen", "JPY", "¥"), CNY("Yuán", "CNY", "¥"), PLN("Złoty", "PLN", "Zł");

	private final String currencyName;
	private final String abbreviation;
	private final String symbol;
}

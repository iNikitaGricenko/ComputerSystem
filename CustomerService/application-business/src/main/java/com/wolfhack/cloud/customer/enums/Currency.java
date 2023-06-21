package com.wolfhack.cloud.customer.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Currency {
    USD("Dollar", "USD", "$"),
    UAH("Hryvnia", "UAH", "₴"),
    EUR("Euro", "EUR", "€"),
    GBP("Pound Sterling", "GBP", "£"),
    JPY("Yen", "JPY", "¥"),
    CNY("Yuán", "CNY", "¥"),
    PLN("Złoty", "PLN", "Zł");

    private final String currencyName;
    private final String abbreviation;
    private final String symbol;
}

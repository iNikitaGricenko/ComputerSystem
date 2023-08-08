package com.wolfhack.cloud.customer.model.enums;

import com.wolfhack.cloud.customer.model.Payment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.regex.Pattern;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {

	MASTERCARD("tok_mastercard", "MasterCard", Pattern.compile("^5[1-5][0-9]{14}$")), VISA("tok_visa", "VISA", Pattern.compile("^4[0-9]{12}(?:[0-9]{3})?$"));

	private final String source;
	private final String cardName;
	private final Pattern pattern;

	public boolean validate(String cardNumber, int expirationYear, int expirationMonth) {
		boolean numberMatches = this.pattern.matcher(cardNumber).matches();

		LocalDate expiresAt = LocalDate.of(expirationYear, expirationMonth, 1);
		boolean notExpired = expiresAt.isAfter(LocalDate.now());

		return numberMatches && notExpired;
	}

	public boolean validate(@Valid Payment payment) {
		int expirationYear = Integer.parseInt(payment.getExpirationYear());
		int expirationMonth = Integer.parseInt(payment.getExpirationMonth());
		return validate(payment.getCardNumber(), expirationYear, expirationMonth);
	}

}

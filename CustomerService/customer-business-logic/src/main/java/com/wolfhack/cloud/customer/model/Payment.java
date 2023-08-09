package com.wolfhack.cloud.customer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Value
public class Payment implements Serializable {

	@NotNull(message = "Payment 'card_number' is required") @NotEmpty(message = "Payment 'card_number' should not be empty") @Pattern(regexp = "[0-9]+", message = "Payment 'card_number' should contain only numbers")
	@JsonProperty("card_number")
	String cardNumber;

	@NotNull(message = "Payment 'expiration_month' is required") @NotEmpty(message = "Payment 'expiration_month' should not be empty") @Pattern(regexp = "[0-9]+", message = "Payment 'expiration_month' should contain only numbers") @Size(min = 1, max = 2, message = "Payment 'expiration_month' length minimum characters is 1 and maximum is 2")
	@JsonProperty("expiration_month")
	String expirationMonth;

	@NotNull(message = "Payment 'expiration_year' is required") @NotEmpty(message = "Payment 'expiration_year' should not be empty") @Size(min = 4, message = "Payment 'expiration_year' length minimum characters is 4") @Pattern(regexp = "[0-9]+", message = "Payment 'expiration_year' should contain only numbers")
	@JsonProperty("expiration_year")
	String expirationYear;

	@NotNull(message = "Payment 'cvc' is required") @NotEmpty(message = "Payment 'cvc' should not be empty") @Size(min = 3, max = 4, message = "Payment 'cvc' length minimum characters is 3 and maximum is 4") @Pattern(regexp = "[0-9]+", message = "Payment 'cvc' should contain only numbers")
	@JsonProperty("cvc")
	String cvc;

}

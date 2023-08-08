package com.wolfhack.cloud.customer.dto;

import com.wolfhack.cloud.customer.model.enums.Currency;
import com.wolfhack.cloud.customer.model.enums.PaymentMethod;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
public class CustomerOrderRequestDTO implements Serializable {
	@NotNull
	@Size(min = 5)
	private final String address;
	private final String description;
	@NotNull private final PaymentMethod paymentMethod;
	@NotNull private final Currency paymentCurrency;
	@NotNull private final String city;
	@NotNull private final String state;
	@NotNull private final String country;
	@NotNull private final String zipCode;
	@NotNull private final CustomerRequestDTO customer;
	@NotNull private final Set<OrderItemRequestDTO> orderItems;
}

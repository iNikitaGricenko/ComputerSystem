package com.wolfhack.cloud.customer.model;

import com.wolfhack.cloud.customer.factory.UpdateFactory;
import com.wolfhack.cloud.customer.model.enums.Currency;
import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrder {

	@RequiredArgsConstructor
	static final class CustomerOrderUpdateFactory implements UpdateFactory {

		private final CustomerOrder customerOrder;

		@Override
		public <T, U> UpdateFactory edit(T editor, Function<T, U> getMethod, Consumer<U> setMethod) {
			Optional.ofNullable(getMethod.apply(editor)).ifPresent(setMethod);
			return this;
		}

		@Override
		public CustomerOrder update() {
			return customerOrder;
		}
	}

	@EqualsAndHashCode.Exclude private Long id;

	private String paymentMethod;

	private Currency paymentCurrency = Currency.EUR;

	private String address;

	private String city;

	private String state;

	private String country;

	private String zipCode;

	private String description;

	@Builder.Default
	@EqualsAndHashCode.Exclude
	private LocalDateTime created = LocalDateTime.now();

	@Builder.Default private OrderStatus status = OrderStatus.INPROGRESS;

	private LocalDateTime completed;

	private Customer customer;

	private boolean isDeleted = false;

	private LocalDateTime deletedAt;

	private Set<OrderItem> orderItems;

	public UpdateFactory renovator() {
		return new CustomerOrderUpdateFactory(this);
	}
}

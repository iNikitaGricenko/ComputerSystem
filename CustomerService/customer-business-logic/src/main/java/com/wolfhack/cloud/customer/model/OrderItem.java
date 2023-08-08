package com.wolfhack.cloud.customer.model;

import com.wolfhack.cloud.customer.factory.UpdateFactory;
import lombok.*;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

	@RequiredArgsConstructor
	static final class OrderItemUpdateFactory implements UpdateFactory {

		private final OrderItem orderItem;

		@Override
		public <T, U> UpdateFactory edit(T editor, Function<T, U> getMethod, Consumer<U> setMethod) {
			Optional.ofNullable(getMethod.apply(editor)).ifPresent(setMethod);
			return this;
		}

		@Override
		public OrderItem update() {
			return orderItem;
		}
	}

	private Long id;

	private String name;

	private String model;

	private long quantity;

	private double unitPrice;

	private String className;

	public UpdateFactory renovator() {
		return new OrderItemUpdateFactory(this);
	}
}

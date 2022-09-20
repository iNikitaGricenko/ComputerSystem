package com.wolfhack.cloud.customer.model;

import com.wolfhack.cloud.customer.factory.UpdateFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @RequiredArgsConstructor
    final class OrderItemUpdateFactory implements UpdateFactory {

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

    @Column(name = "order_item_name")
    private String name;

    @Column(name = "order_item_model")
    private String model;

    @Column(name = "quantity")
    private long quantity;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "order_item_class_name")
    private String className;

    public UpdateFactory renovator() {
        return new OrderItemUpdateFactory(this);
    }
}

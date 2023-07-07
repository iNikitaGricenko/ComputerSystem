package com.wolfhack.cloud.customer.model;

import com.wolfhack.cloud.customer.factory.UpdateFactory;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsExclude;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @RequiredArgsConstructor
    static final class CustomerUpdateFactory implements UpdateFactory {

        private final Customer customer;

        @Override
        public <T, U> UpdateFactory edit(T editor, Function<T, U> getMethod, Consumer<U> setMethod) {
            Optional.ofNullable(getMethod.apply(editor)).ifPresent(setMethod);
            return this;
        }

        @Override
        public Customer update() {
            return customer;
        }
    }

    @EqualsAndHashCode.Exclude
    private Long id;

    private String firstName;

    private String secondName;

    private String phone;

    private String email;

    @EqualsAndHashCode.Exclude
    private LocalDateTime registerDate = LocalDateTime.now();

    public UpdateFactory renovator() {
        return new CustomerUpdateFactory(this);
    }
}

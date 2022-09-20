package com.wolfhack.cloud.customer.model;

import com.wolfhack.cloud.customer.factory.UpdateFactory;
import com.wolfhack.cloud.customer.model.enums.Currency;
import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

@Builder
@Getter @Setter
@Entity(name = "customer_order")
@SQLDelete(sql = "UPDATE customer_order e " +
        "SET deleted=true, deleted_at=now() " +
        "WHERE e.customer_order_id=?")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrder {

    @RequiredArgsConstructor
    final class CustomerOrderUpdateFactory implements UpdateFactory {

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_order_id")
    private Long id;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_currency", nullable = false)
    private Currency paymentCurrency = Currency.EUR;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "description", length = 1024)
    private String description;

    @Builder.Default
    @Column(name = "created", insertable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status = OrderStatus.INPROGRESS;

    @Column(name = "completed", insertable = false)
    private LocalDateTime completed;

    @ManyToOne(
            targetEntity = Customer.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @Column(name = "deleted")
    private boolean isDeleted = false;

    @Column(name = "deleted_at", insertable = false)
    private LocalDateTime deletedAt;

    @ElementCollection
    @CollectionTable(name = "customer_order_items", joinColumns = {
            @JoinColumn(name = "customer_order_id", referencedColumnName = "customer_order_id")})
    private Set<OrderItem> orderItems;

    public UpdateFactory renovator() {
        return new CustomerOrderUpdateFactory(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        CustomerOrder customerOrder = (CustomerOrder) o;
        return id != null && Objects.equals(id, customerOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

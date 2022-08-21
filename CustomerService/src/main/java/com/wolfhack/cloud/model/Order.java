package com.wolfhack.cloud.model;

import com.wolfhack.cloud.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @Column(name = "description", length = 1024)
    private String description;

    @Column(name = "created", insertable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status = OrderStatus.INPROGRESS;

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @Column(name = "deleted")
    private boolean isDeleted = Boolean.FALSE;
    @Column(name = "deleted_at", insertable = false)
    private LocalDateTime deletedAt;

    @Column(name = "count")
    private int count;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Order order = (Order) o;
        return id != null && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package com.wolfhack.cloud.adapter.persistence.entity;

import com.wolfhack.cloud.adapter.persistence.enums.AuthorizationStatus;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "authorization_log")
public class EntityAuthorizationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", unique = true)
    private Long id;

    @Builder.Default
    @Column(name = "date_time")
    private LocalDateTime dateTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private AuthorizationStatus authorizationStatus;

    @ManyToOne(targetEntity = EntityUser.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ToString.Exclude
    private EntityUser user;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        EntityAuthorizationLog authorizationLog = (EntityAuthorizationLog) o;
        return id != null && Objects.equals(id, authorizationLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

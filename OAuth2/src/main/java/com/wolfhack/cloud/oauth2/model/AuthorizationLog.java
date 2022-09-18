package com.wolfhack.cloud.oauth2.model;

import com.wolfhack.cloud.oauth2.enums.AuthorizationStatus;
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
public class AuthorizationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", unique = true)
    private Long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private AuthorizationStatus authorizationStatus;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ToString.Exclude
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        AuthorizationLog authorizationLog = (AuthorizationLog) o;
        return id != null && Objects.equals(id, authorizationLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

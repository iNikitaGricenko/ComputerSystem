package com.wolfhack.cloud.oauth2.model;

import com.wolfhack.cloud.oauth2.enums.Role;
import com.wolfhack.cloud.oauth2.factory.UpdateFactory;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @RequiredArgsConstructor
    final class UserUpdateFactory implements UpdateFactory {

        private final User user;

        @Override
        public <U, T> UserUpdateFactory update(U editor, Function<U, T> getMethod, Consumer<T> setMethod) {
            Optional.ofNullable(getMethod.apply(editor)).ifPresent(setMethod);
            return this;
        }

        public User build() {
            return user;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "photo")
    private String photo;

    @Column(name = "active")
    private boolean active;

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "activation_url")
    private String activationUrl;

    @Column(name = "register_date")
    private LocalDateTime registerDate = LocalDateTime.now();

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Role role;

    public boolean isAdmin() {
        return Objects.equals(role.toString(), "ADMIN");
    }

    public UpdateFactory renovator() {
        return new UserUpdateFactory(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        User user = (User) o;
        boolean idEquals = id != null && Objects.equals(id, user.id);
        boolean loginEquals = login != null && Objects.equals(login, user.login);
        return idEquals & loginEquals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

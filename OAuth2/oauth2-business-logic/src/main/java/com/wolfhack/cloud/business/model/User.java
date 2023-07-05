package com.wolfhack.cloud.business.model;

import com.wolfhack.cloud.business.enums.Role;
import com.wolfhack.cloud.business.factory.UpdateFactory;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Data
@Builder
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

    private Long id;

    private String login;

    private String username;

    private String password;

    private String photo;

    private boolean active;

    private String activationCode;

    private String activationUrl;

    private LocalDateTime registerDate;

    private LocalDateTime lastLoginDate;

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
        if (o == null || getClass() != o.getClass()) {
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

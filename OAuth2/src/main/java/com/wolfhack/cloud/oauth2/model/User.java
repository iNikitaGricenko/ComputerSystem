package com.wolfhack.cloud.oauth2.model;

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
@Entity(name = "users")
public class User {

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

    @Enumerated(EnumType.STRING)
    private Role role;

    public boolean isAdmin() {
        return Objects.equals(role.toString(), "ADMIN");
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

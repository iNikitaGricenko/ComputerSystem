package com.wolfhack.cloud.model.entity;

import com.wolfhack.cloud.model.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table("user_entity")
@Getter
@Setter
public class UserEntity {
	@Id private Long id;

	@Column("email") private String email;

	@Column("password") private String password;

	@Column("first_name") private String firstName;

	@Column("last_name") private String lastName;

	@Column("role") private Role role;

	@Column("registered_at") private LocalDate registeredAt;

	@Column("online_at") private LocalDateTime onlineAt;

	@Column("deleted") private boolean deleted = false;

	@Column("deleted_at") private LocalDateTime deletedAt;

}

package com.wolfhack.cloud.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CustomerResponseDTO implements Serializable {
	@Schema(example = "1") private final Long id;
	@Schema(example = "email@domain.com") private final String firstName;
	@Schema(example = "Nikit") private final String secondName;
	@Schema(example = "Sambatist") private final String phone;
	@Schema(example = "+111 (202) 555-0125") private final String email;
	@Schema(example = "2007-12-03T10:15:30") private final LocalDateTime registerDate;
}

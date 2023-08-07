package com.wolfhack.cloud.business.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Value
public class UserEmailData implements Serializable {

	String activationCode;
	String email;

}

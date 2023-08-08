package com.wolfhack.cloud.email.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

	private String activationCode;
	private String email;

}

package com.wolfhack.cloud.model;

import java.io.Serializable;

public record MessagePOJO(String message, String registrationToken) implements Serializable {
}

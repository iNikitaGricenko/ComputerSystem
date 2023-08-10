package com.wolfhack.cloud.model;

import java.io.Serializable;

public record SubscriptionPOJO(String topic, String registrationToken) implements Serializable {
}

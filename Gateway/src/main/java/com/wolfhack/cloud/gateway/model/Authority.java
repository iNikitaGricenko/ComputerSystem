package com.wolfhack.cloud.gateway.model;

import java.io.Serializable;
import java.util.List;

public record Authority(Long id, String email, List<String> authorities) implements Serializable {
}
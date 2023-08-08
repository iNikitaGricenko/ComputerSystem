package com.wolfhack.cloud.model.dto;

import java.io.Serializable;
import java.util.List;

public record UserAuthorityInfo(Long id, String email,
                                List<String> authorities) implements Serializable {
}

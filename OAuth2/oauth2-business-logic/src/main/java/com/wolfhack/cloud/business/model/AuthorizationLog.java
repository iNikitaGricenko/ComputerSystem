package com.wolfhack.cloud.business.model;

import com.wolfhack.cloud.business.enums.AuthorizationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
public class AuthorizationLog {

    private Long id;

    private LocalDateTime dateTime;

    private AuthorizationStatus authorizationStatus;

    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthorizationLog authorizationLog = (AuthorizationLog) o;
        return id != null && Objects.equals(id, authorizationLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

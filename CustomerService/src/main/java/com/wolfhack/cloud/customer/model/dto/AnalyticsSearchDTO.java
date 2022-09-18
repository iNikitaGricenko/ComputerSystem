package com.wolfhack.cloud.customer.model.dto;

import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class AnalyticsSearchDTO implements Serializable {

    @NotNull
    @Schema(example = "DELIVERED")
    @Parameter(name = "status", example = "DELIVERED")
    private final OrderStatus status;

    @NotNull
    @Schema(example = "2012-12-03")
    @Parameter(name = "from", example = "2012-12-03")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate from;

    @NotNull
    @Schema(example = "2013-12-03")
    @Parameter(name = "to", example = "2013-12-03")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate to;

}

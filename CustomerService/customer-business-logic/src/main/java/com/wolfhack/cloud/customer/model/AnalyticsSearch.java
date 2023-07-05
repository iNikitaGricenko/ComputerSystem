package com.wolfhack.cloud.customer.model;

import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class AnalyticsSearch implements Serializable {

    @Schema(example = "INPROGRESS")
    private final OrderStatus status;

    @Schema(example = "2007-12-03", description = "years-month-days")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate from;

    @Schema(example = "2022-09-26", description = "years-month-days")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate to;

}
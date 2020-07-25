package com.javastart.customerservice.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class BillResponseDto {

    private Long billId;

    private Long accountId;

    private BigDecimal amount;

    private Boolean overdraftEnabled;

    private Boolean isDefault;

    private OffsetDateTime createDate;
}

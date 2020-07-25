package com.javastart.customerservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class BillDto {
    @JsonProperty("account_id")
    private Long accountId;

    private Boolean isDefault;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("overdraft")
    private Boolean overdraftEnabled;

}

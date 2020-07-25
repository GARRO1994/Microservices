package com.javastart.customerservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillRequestDto {

    @NotNull
    private Long accountId;

    private Boolean isDefault;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("overdraft")
    private Boolean overdraftEnabled;

}

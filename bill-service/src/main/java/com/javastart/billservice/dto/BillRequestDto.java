package com.javastart.billservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class BillRequestDto {

    @NotNull
    @JsonProperty("account_id")
    private Long accountId;

    private Boolean isDefault;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("overdraft")
    private Boolean overdraftEnabled;

}

package com.javastart.customerservice.transfer.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransferResponseDTO {

    private String emailFrom;

    private String emailTo;

    private BigDecimal amount;
}

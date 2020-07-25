package com.javastart.customerservice.transfer.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequestDTO {

    private Long accountIdFrom;

    private Long accountIdTo;

    private BigDecimal amount;

    private Long billIdFrom;

    private Long billIdTo;
}

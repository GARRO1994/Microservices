package com.javastart.customerservice.deposit.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DepositResponseDTO implements Serializable {

    private static final long serialVersionUID = 10;

    private Long billId;

    private String email;
}

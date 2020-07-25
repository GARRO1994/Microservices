package com.javastart.notification.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositResponseDTO implements Serializable {

    private static final long serialVersionUID = 10;

    private Long billId;

    private String email;
}

package com.javastart.customerservice.deposit.controller;

import com.javastart.customerservice.deposit.controller.dto.DepositRequestDTO;
import com.javastart.customerservice.deposit.controller.dto.DepositResponseDTO;
import com.javastart.customerservice.deposit.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepositController {

    private final DepositService depositService;

    @Autowired
    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/deposit")
    public Object deposit(@RequestBody DepositRequestDTO requestDTO) {
        return depositService.deposit(requestDTO.getAccountId(), requestDTO.getBillId(), requestDTO.getAmount());
    }
}
// получение депозита по id,
// billId и email(может возвращать список)
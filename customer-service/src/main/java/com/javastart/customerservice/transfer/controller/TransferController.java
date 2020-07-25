package com.javastart.customerservice.transfer.controller;

import com.javastart.customerservice.transfer.Service.TransferService;
import com.javastart.customerservice.transfer.controller.dto.TransferRequestDTO;
import com.javastart.customerservice.transfer.controller.dto.TransferResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {

    private final TransferService transferService;


    @Autowired
    public TransferController(TransferService transferService){
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public TransferResponseDTO transfer(@RequestBody TransferRequestDTO transferRequestDTO){
        return transferService.transfer(transferRequestDTO.getAccountIdFrom(),transferRequestDTO.getAccountIdTo()
                ,transferRequestDTO.getAmount(),transferRequestDTO.getBillIdFrom(),transferRequestDTO.getBillIdTo());
    }
}


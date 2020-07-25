package com.javastart.customerservice.deposit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javastart.customerservice.controller.dto.AccountResponseDto;
import com.javastart.customerservice.controller.dto.BillDto;
import com.javastart.customerservice.controller.dto.BillResponseDto;
import com.javastart.customerservice.deposit.controller.dto.DepositResponseDTO;
import com.javastart.customerservice.deposit.entity.Deposit;
import com.javastart.customerservice.deposit.repository.DepositRepository;
import com.javastart.customerservice.rest.AccountRestService;
import com.javastart.customerservice.rest.BillRestService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
public class DepositService {

    private final DepositRepository depositRepository;

    private final AccountRestService accountRestService;

    private final BillRestService billRestService;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public DepositService(DepositRepository depositRepository
            , AccountRestService accountRestService, BillRestService billRestService, RabbitTemplate rabbitTemplate) {
        this.depositRepository = depositRepository;
        this.accountRestService = accountRestService;
        this.billRestService = billRestService;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Object deposit(Long accountId, Long billId, BigDecimal amount) {
        if (billId != null) {
            BillResponseDto billResponseDTO = billRestService.getBillById(billId);
            BillDto billRequestDto = BillDto.builder()
                    .accountId(billResponseDTO.getAccountId())
                    .amount(billResponseDTO.getAmount().add(amount))
                    .overdraftEnabled(billResponseDTO.getOverdraftEnabled()).build();
            billRestService.putBill(billId, billRequestDto);
            AccountResponseDto account = accountRestService.getAccountById(billResponseDTO.getAccountId());
            depositRepository.save(new Deposit(billId, account.getEmail(), amount, OffsetDateTime.now()));
            DepositResponseDTO depositResponseDTO = new DepositResponseDTO(billId, account.getEmail());
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                rabbitTemplate.convertAndSend("js.deposit.notify.exchange", "js.deposit",objectMapper.writeValueAsString(depositResponseDTO));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return depositResponseDTO;
        }
        BillResponseDto defaultBill = billRestService.getDefaultBill(accountId);
        BillDto billRequestDto = BillDto.builder()
                .accountId(accountId)
                .amount(defaultBill.getAmount().add(amount))
                .isDefault(defaultBill.getIsDefault())
                .overdraftEnabled(defaultBill.getOverdraftEnabled()).build();
        billRestService.putBill(defaultBill.getBillId(), billRequestDto);
        AccountResponseDto account = accountRestService.getAccountById(accountId);
        depositRepository.save(new Deposit(defaultBill.getBillId(), account.getEmail(), amount, OffsetDateTime.now()));
        return new DepositResponseDTO(defaultBill.getBillId(), account.getEmail());

    }


}
//если в аккунте 1 билл то он дефолтный
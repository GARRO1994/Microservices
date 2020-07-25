package com.javastart.customerservice.transfer.Service;

import com.javastart.customerservice.controller.dto.AccountResponseDto;
import com.javastart.customerservice.controller.dto.BillDto;
import com.javastart.customerservice.controller.dto.BillResponseDto;
import com.javastart.customerservice.exceptions.NotEnoughMoneyException;
import com.javastart.customerservice.rest.AccountRestService;
import com.javastart.customerservice.rest.BillRestService;
import com.javastart.customerservice.transfer.controller.dto.TransferResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class TransferService {

    private final BillRestService billRestService;

    private final AccountRestService accountRestService;

    @Autowired
    public TransferService(BillRestService billRestService, AccountRestService accountRestService) {
        this.billRestService = billRestService;
        this.accountRestService = accountRestService;
    }

    public TransferResponseDTO transfer(Long accountIdFrom, Long accountIdTo,
                                        BigDecimal amount, Long billIdFrom, Long billIdTo) {
        if (billIdFrom != null && billIdTo != null) {
            BillResponseDto billResponseDtoFrom = billRestService.getBillById(billIdFrom);
            BillResponseDto billResponseDtoTo = billRestService.getBillById(billIdTo);
            if (billResponseDtoFrom.getAmount().compareTo(amount) < 0) {
                throw new NotEnoughMoneyException("Not enough money on bill: " + billIdFrom);
            }
            BillDto billDtoFrom = BillDto.builder()
                    .accountId(billResponseDtoFrom.getAccountId())
                    .amount(billResponseDtoFrom.getAmount().subtract(amount))
                    .isDefault(billResponseDtoFrom.getIsDefault())
                    .overdraftEnabled(billResponseDtoFrom.getOverdraftEnabled())
                    .build();

            BillDto billDtoTo = BillDto.builder()
                    .accountId(billResponseDtoTo.getAccountId())
                    .amount(billResponseDtoTo.getAmount().add(amount))
                    .isDefault(billResponseDtoTo.getIsDefault())
                    .overdraftEnabled(billResponseDtoTo.getOverdraftEnabled())
                    .build();
            billRestService.updateBills(billIdFrom, billIdTo, Arrays.asList(billDtoFrom, billDtoTo));
            String emailFrom = accountRestService.getAccountById(billResponseDtoFrom.getAccountId()).getEmail();
            String emailTo = accountRestService.getAccountById(billResponseDtoTo.getAccountId()).getEmail();
            return new TransferResponseDTO(emailFrom, emailTo, amount);
        }
        return null;
    }
}

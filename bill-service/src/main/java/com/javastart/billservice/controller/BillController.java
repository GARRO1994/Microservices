package com.javastart.billservice.controller;

import com.javastart.billservice.dto.BillRequestDto;
import com.javastart.billservice.dto.BillResponseDto;
import com.javastart.billservice.exceptions.BillOverdraftException;
import com.javastart.billservice.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/{id}")
    public BillResponseDto getBill(@PathVariable("id") Long billId) {
        return new BillResponseDto(billService.getBillById(billId));
    }

    @GetMapping("/account/{accountId}")
    public List<BillResponseDto> getBillByAccountId(@PathVariable("accountId") Long accountId) {
        return billService.getBillsByAccountId(accountId).stream()
                .map(BillResponseDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    public Long createBill(@Valid @RequestBody BillRequestDto billRequestDto) throws BillOverdraftException {
        if ((billRequestDto.getAmount().compareTo(BigDecimal.ZERO) > 0) && !billRequestDto.getOverdraftEnabled()) {
            return billService.saveBill(billRequestDto.getAccountId(), billRequestDto.getIsDefault(), billRequestDto.getAmount(),
                    billRequestDto.getOverdraftEnabled());
        } else {
            throw new BillOverdraftException("This balance is negative. Enter correct amount from this bill.");
        }
    }


    @PostMapping("/list")
    public List<Long> createBills(@RequestBody List<BillRequestDto> billRequestDtos) {
        return billService.saveAll(billRequestDtos);
    }

    @GetMapping("/")
    public List<BillResponseDto> getAllBills() {
        return billService.findAll().stream()
                .map(BillResponseDto::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public BillResponseDto updateBill(@PathVariable("id") Long billId,
                                      @Valid @RequestBody BillRequestDto billRequestDto) throws BillOverdraftException {
        if ((billRequestDto.getAmount().compareTo(BigDecimal.ZERO) < 0) && !billRequestDto.getOverdraftEnabled()) {
            throw new BillOverdraftException("This change is wrong! Enter correct bill arguments.");
        }
        return new BillResponseDto(billService.updateBill(billId, billRequestDto.getIsDefault(), billRequestDto.getAccountId(),
                billRequestDto.getAmount(), billRequestDto.getOverdraftEnabled()));
    }

    @PutMapping("/list")
    public List<BillResponseDto> updateBills(@RequestParam(value = "bill_id_from") Long billIdFrom,
                                             @RequestParam(value = "bill_id_to") Long billIdTo,
                                             @RequestBody List<BillRequestDto> billRequestDtos) {
        return billService.updateBills(billIdFrom, billIdTo, billRequestDtos).stream()
                .map(BillResponseDto::new).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public BillResponseDto deleteBill(@PathVariable("id") Long billId) {
        return new BillResponseDto(billService.deleteBill(billId));
    }
}

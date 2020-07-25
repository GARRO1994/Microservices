package com.javastart.customerservice.rest;

import com.javastart.customerservice.controller.dto.BillDto;
import com.javastart.customerservice.controller.dto.BillRequestDto;
import com.javastart.customerservice.controller.dto.BillResponseDto;
import com.javastart.customerservice.exceptions.CustomerServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class BillRestService {

    private final RestTemplate restTemplate;

    @Value("${internal.bill.service.url}")
    private String billServiceUrl;

    @Autowired
    public BillRestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BillResponseDto getBillById(Long billId) {
        StringBuilder sb = new StringBuilder(billServiceUrl);
        sb.append(billId);
        ResponseEntity<BillResponseDto> billResponse = restTemplate
                .getForEntity(sb.toString(), BillResponseDto.class);
        return billResponse.getBody();
    }


    public void putBill(Long billId, BillDto billRequestDto) {
        StringBuilder sb = new StringBuilder(billServiceUrl);
        sb.append(billId);
        restTemplate.put(sb.toString(), billRequestDto);
    }

    public BillResponseDto getDefaultBill(Long accountId) {
        StringBuilder sb = new StringBuilder(billServiceUrl);
        sb.append("account").append("/").append(accountId);
        ResponseEntity<BillResponseDto[]> billResponse = restTemplate.getForEntity(sb.toString(), BillResponseDto[].class);
        List<BillResponseDto> billsResponseDto = Arrays.asList(Objects.requireNonNull(billResponse.getBody()));
        return billsResponseDto.stream()
                .filter(BillResponseDto::getIsDefault)
                .findAny()
                .orElseThrow(() -> new CustomerServiceException("Deposit exception"));
    }

    public void updateBills(Long billIdFrom, Long billIdTo, List<BillDto> bills) {
        StringBuilder sb = new StringBuilder(billServiceUrl);
        sb.append("list").append("?").append("bill_id_from").append(billIdFrom)
        .append("&").append("bill_id_to").append(billIdTo);
        restTemplate.put(sb.toString(),bills);
    }
}

package com.javastart.customerservice.customer.service;

import com.javastart.customerservice.customer.controller.dto.CustomerRequestDto;
import com.javastart.customerservice.customer.controller.dto.CustomerResponseDTO;
import com.javastart.customerservice.controller.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Value("${internal.account.service.url}")
    private String accountServiceUrl;

    @Value("${internal.bill.service.url}")
    private String billServiceUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public CustomerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CustomerResponseDTO getCustomerEmail(String email) {
        StringJoiner accountUrlBuilder = new StringJoiner("");
        accountUrlBuilder.add(accountServiceUrl).add("email/").add(email);
        ResponseEntity<AccountResponseDto> accountResponse = restTemplate
                .getForEntity(accountUrlBuilder.toString(), AccountResponseDto.class);
        AccountResponseDto accountDto = accountResponse.getBody();
        Long accountId = accountDto.getAccountId();
        List<BillResponseDto> billDto = getBillsByAccountId(accountId);
        return new CustomerResponseDTO(accountDto, billDto);
    }

    public CustomerResponseDTO getCustomerById(Long id) {
        StringJoiner accountUrlBuilder = new StringJoiner(accountServiceUrl);
        accountUrlBuilder.add(String.valueOf(id));
        ResponseEntity<AccountResponseDto> accountResponse = restTemplate
                .getForEntity(accountUrlBuilder.toString(), AccountResponseDto.class);
        AccountResponseDto accountDto = accountResponse.getBody();
        Long accountId = accountDto.getAccountId();
        List<BillResponseDto> billDTO = getBillsByAccountId(id);
        return new CustomerResponseDTO(accountDto, billDTO);
    }

    public String createCustomer(CustomerRequestDto customerRequestDto) {
        AccountRequestDto accountRequestDto = customerRequestDto.getAccountRequestDto();
        ResponseEntity<Long> accountResponseId = restTemplate.postForEntity(accountServiceUrl, accountRequestDto, Long.class);
        Long accountId = accountResponseId.getBody();
        List<BillDto> billRequestBody = customerRequestDto.getBillRequestDto().stream()
                .map(request -> new BillDto(accountId, request.getIsDefault(), request.getAmount(), request.getOverdraftEnabled()))
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder(billServiceUrl);
        sb.append("/list");
        ResponseEntity<Long[]> billsResponse = restTemplate.postForEntity(sb.toString(), billRequestBody, Long[].class);
        List<Long> billIds = Arrays.asList(Objects.requireNonNull(billsResponse.getBody()));
        AccountDto accountDto = new AccountDto(accountRequestDto.getName(), accountRequestDto.getEmail(), accountRequestDto.getPhone(), billIds);
        restTemplate.put(accountServiceUrl.concat("/").concat(String.valueOf(accountId)), accountDto);
        return "ALL is Success!!!";
    }

    private List<BillResponseDto> getBillsByAccountId(Long accountId) {
        StringJoiner billUrlBuilder = new StringJoiner("");
        billUrlBuilder.add(billServiceUrl).add("account/").add(String.valueOf(accountId));

        ResponseEntity<BillResponseDto[]> billResponse = restTemplate
                .getForEntity(billUrlBuilder.toString(), BillResponseDto[].class);
        return Arrays.asList(Objects.requireNonNull(billResponse.getBody()));
    }
}
//если в аккунте 1 билл то он дефолтный
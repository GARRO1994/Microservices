package com.javastart.customerservice.rest;

import com.javastart.customerservice.controller.dto.AccountResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountRestService {

    private final RestTemplate restTemplate;

    @Value("${internal.account.service.url}")
    private String accountServiceUrl;

    @Autowired
    public AccountRestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AccountResponseDto getAccountById(Long accountId) {
        ResponseEntity<AccountResponseDto> accountResponse = restTemplate
                .getForEntity(accountServiceUrl
                        .concat(String.valueOf(accountId)), AccountResponseDto.class);
        return accountResponse.getBody();
    }
}

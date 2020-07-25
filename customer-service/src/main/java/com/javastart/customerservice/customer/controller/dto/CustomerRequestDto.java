package com.javastart.customerservice.customer.controller.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.javastart.customerservice.controller.dto.AccountRequestDto;
import com.javastart.customerservice.controller.dto.BillRequestDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CustomerRequestDto {

    @JsonProperty("accountRequestDTO")
    private AccountRequestDto accountRequestDto;

    @JsonProperty("billRequestDTO")
    private List<BillRequestDto> billRequestDto;

}

package com.javastart.customerservice.customer.controller.dto;

import com.javastart.customerservice.controller.dto.AccountResponseDto;
import com.javastart.customerservice.controller.dto.BillResponseDto;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerResponseDTO {

    @NonNull
    private AccountResponseDto accountResponseDto;

    @NotNull
    private List <BillResponseDto> billResponseDto;
}

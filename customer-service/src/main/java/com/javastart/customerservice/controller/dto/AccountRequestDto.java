package com.javastart.customerservice.controller.dto;

import com.sun.istack.internal.NotNull;
import lombok.Data;

@Data
public class AccountRequestDto {
    private String name;

    private String email;

    private String phone;


}

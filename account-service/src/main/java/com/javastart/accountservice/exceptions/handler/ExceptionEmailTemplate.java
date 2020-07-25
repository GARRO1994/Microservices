package com.javastart.accountservice.exceptions.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ExceptionEmailTemplate {

    private OffsetDateTime time;

    private String message;

    private int status;

    private String exception;

}

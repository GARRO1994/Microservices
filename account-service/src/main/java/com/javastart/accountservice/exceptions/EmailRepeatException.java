package com.javastart.accountservice.exceptions;

public class EmailRepeatException extends RuntimeException{
    public EmailRepeatException(String message) {
        super(message);
    }
}

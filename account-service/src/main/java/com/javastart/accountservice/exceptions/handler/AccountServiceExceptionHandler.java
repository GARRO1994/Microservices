package com.javastart.accountservice.exceptions.handler;

import com.javastart.accountservice.exceptions.AccountNotFoundException;
import com.javastart.accountservice.exceptions.EmailRepeatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class AccountServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AccountNotFoundException.class})
    public ResponseEntity<ExceptionResponseTemplate> handleAccountNotFoundException(AccountNotFoundException ex){
        return new ResponseEntity<>(new ExceptionResponseTemplate(OffsetDateTime.now(),ex.getMessage(),
                403,"NotFoundExceptionAccount"), HttpStatus.NOT_FOUND);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        List<String> errors = allErrors.stream().map(objectError
                -> ((FieldError) objectError).getField()).collect(Collectors.toList());
        String message = "Validation failed for request params. Account";
        return new ResponseEntity<>(new ExceptionArgumentsResponseTemplate(message, 401,
                "ArgumentNotValidException. Account", errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EmailRepeatException.class})
    public ResponseEntity<ExceptionEmailTemplate> handleMailRepeatException(EmailRepeatException ex){
        return new ResponseEntity<>(new ExceptionEmailTemplate(OffsetDateTime.now(),ex.getMessage(),
                402,"Email is repeat please enter another"), HttpStatus.NOT_FOUND);
    }
}

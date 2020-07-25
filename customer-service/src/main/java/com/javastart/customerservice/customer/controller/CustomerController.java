package com.javastart.customerservice.customer.controller;

import com.javastart.customerservice.customer.controller.dto.CustomerRequestDto;
import com.javastart.customerservice.customer.controller.dto.CustomerResponseDTO;
import com.javastart.customerservice.customer.service.CustomerService;
import com.javastart.customerservice.exceptions.CustomerServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public CustomerResponseDTO getCustomer(@RequestParam(value = "email", required = false) String email,
                                           @RequestParam(value = "accountId", required = false) Long accountId) {
        if ((email == null || email.isEmpty()) && accountId == null) {
            throw new CustomerServiceException("Unable to find customer, because no data provided");
        }
        if (email != null && !email.isEmpty()) {
            return customerService.getCustomerEmail(email);
        } else {
            return customerService.getCustomerById(accountId);
        }
    }

    @PostMapping("/")
    public Object createCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        return customerService.createCustomer(customerRequestDto);
    }
}

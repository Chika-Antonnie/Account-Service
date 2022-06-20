package com.chika.accountservice.controller;

import com.chika.accountservice.data.dto.CreateAccountRequest;
import com.chika.accountservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    public ResponseEntity findCustomer(@PathVariable(name = "customerId") long customerId){
        log.info("find customer request for customerId: {}",customerId);
        return ResponseEntity.ok(customerService.getCustomerByCustomerId(customerId));
    }

    @PutMapping("/create-account")
    public ResponseEntity createAccount(@RequestBody CreateAccountRequest request){
        log.info("find customer request for customerId: {}",request.getCustomerId());
        return ResponseEntity.ok(customerService.createAccount(request));
    }
}

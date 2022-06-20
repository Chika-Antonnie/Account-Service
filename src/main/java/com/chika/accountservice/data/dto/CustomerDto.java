package com.chika.accountservice.data.dto;


import com.chika.accountservice.data.entity.Account;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDto {
    private String firstName;
    private String lastName;
    private long customerId;
    private List<Account> accounts;
}

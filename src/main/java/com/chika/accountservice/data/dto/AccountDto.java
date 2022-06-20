package com.chika.accountservice.data.dto;

import com.chika.accountservice.data.entity.Customer;
import com.chika.accountservice.util.enums.AccountStatus;
import com.chika.accountservice.util.enums.AccountType;
import com.chika.accountservice.util.enums.UpdateStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {
    private Customer customer;

    private String accountNumber;

    private AccountType type;

    private AccountStatus status;

    private BigDecimal availableBalance;

    private BigDecimal balance;

    private UpdateStatus lastBalanceUpdateStatus;

}

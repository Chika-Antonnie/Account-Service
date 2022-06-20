package com.chika.accountservice.service;

import com.chika.accountservice.data.dto.AccountDto;
import com.chika.accountservice.data.dto.CreateAccountRequest;
import com.chika.accountservice.data.dto.CustomerDto;
import com.chika.accountservice.data.entity.Account;
import com.chika.accountservice.data.entity.Customer;
import com.chika.accountservice.repository.AccountRepository;
import com.chika.accountservice.repository.CustomerRepository;
import com.chika.accountservice.util.AccountNoGenerator;
import com.chika.accountservice.util.converters.AccountConverter;
import com.chika.accountservice.util.converters.CustomerConverter;
import com.chika.accountservice.util.enums.AccountStatus;
import com.chika.accountservice.util.enums.AccountType;
import com.chika.accountservice.util.enums.UpdateStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public CustomerDto getCustomerByCustomerId(long customerId){
        CustomerConverter converter = new CustomerConverter();
        Optional<Customer> customerOptional = customerRepository.findByCustomerId(customerId);

        if(customerOptional.isPresent()){
            return converter.convertToDto(customerOptional.get(),null);
        }

        return converter.convertToDto(new Customer(), null);
    }

    public AccountDto createAccount(CreateAccountRequest req){
        Optional<Customer> customerOptional = customerRepository.findByCustomerId(req.getCustomerId());
        Customer customer = customerOptional.get();

        Account account = new Account();
        account.setAccountNumber(AccountNoGenerator.generate());
        account.setCustomer(customer);
        account.setType(AccountType.CURRENT);
        account.setAvailableBalance(req.getInitialCredit());
        account.setBalance(req.getInitialCredit());
        account.setStatus(AccountStatus.ACTIVE);
        account.setLastBalanceUpdateStatus(UpdateStatus.PENDING);
        Account createdAccount = accountRepository.save(account);

        List<Account> accounts = new ArrayList<>();
        accounts.add(createdAccount);
        customer.setAccounts(accounts);
        customerRepository.save(customer);

        return new AccountConverter().convertToDto(createdAccount, null);
    }
}

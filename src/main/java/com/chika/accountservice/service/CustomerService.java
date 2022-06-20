package com.chika.accountservice.service;

import com.chika.accountservice.data.dto.AccountDto;
import com.chika.accountservice.data.dto.CreateAccountRequest;
import com.chika.accountservice.data.dto.CustomerDto;
import com.chika.accountservice.data.entity.Account;
import com.chika.accountservice.data.entity.Customer;
import com.chika.accountservice.exception.CreateTransactionException;
import com.chika.accountservice.repository.AccountRepository;
import com.chika.accountservice.repository.CustomerRepository;
import com.chika.accountservice.rest.RestConnector;
import com.chika.accountservice.util.AccountNoGenerator;
import com.chika.accountservice.util.converters.AccountConverter;
import com.chika.accountservice.util.converters.CustomerConverter;
import com.chika.accountservice.util.enums.AccountStatus;
import com.chika.accountservice.util.enums.AccountType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final RestConnector restConnector;

    @Value("${transaction.service.url}")
    private String transactionServiceUrl;

    public CustomerDto getCustomerByCustomerId(long customerId){
        CustomerConverter converter = new CustomerConverter();
        Optional<Customer> customerOptional = customerRepository.findByCustomerId(customerId);

        if(customerOptional.isPresent()){
            return converter.convertToDto(customerOptional.get());
        }

        return converter.convertToDto(new Customer());
    }

    @Transactional(rollbackFor = CreateTransactionException.class)
    public AccountDto createAccount(CreateAccountRequest req) throws CreateTransactionException {
        Optional<Customer> customerOptional = customerRepository.findByCustomerId(req.getCustomerId());
        Customer customer = customerOptional.get();

        Account account = new Account();
        account.setAccountNumber(AccountNoGenerator.generate());
        account.setCustomer(customer);
        account.setType(AccountType.CURRENT);
        account.setAvailableBalance(req.getInitialCredit());
        account.setBalance(req.getInitialCredit());
        account.setStatus(AccountStatus.ACTIVE);
        Account createdAccount = accountRepository.save(account);

        if(!BigDecimal.ZERO.equals(req.getInitialCredit())){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("accountNo", createdAccount.getAccountNumber());
            jsonObject.put("amount", req.getInitialCredit());
            jsonObject.put("transactionType", "CREDIT");

            ResponseEntity<String> apiResponse = restConnector.sendPostRequest(transactionServiceUrl, jsonObject.toJSONString(), MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);

            if(!apiResponse.getStatusCode().is2xxSuccessful()){
                throw new CreateTransactionException("Create transaction failed with response code: "+apiResponse.getStatusCode());
            }
        }


        List<Account> accounts = new ArrayList<>();
        accounts.add(createdAccount);
        customer.setAccounts(accounts);
        customerRepository.save(customer);

        return new AccountConverter().convertToDto(createdAccount);
    }

    public Map<String, Object> getTransactions(String accountNumber) throws ParseException {
        Optional<Account> accountOptional = accountRepository.findByAccountNumber(accountNumber);
        Map<String, Object> responseMap = new HashMap<>();
        Account account = accountOptional.get();
        responseMap.put("accountNumber", account.getAccountNumber());
        responseMap.put("firstName", account.getCustomer().getFirstName());
        responseMap.put("lastName", account.getCustomer().getLastName());
        responseMap.put("availableBalance", account.getAvailableBalance());

        String queryParam = "?accountNumber=".concat(accountNumber);
        transactionServiceUrl = transactionServiceUrl.concat(queryParam);
        String apiResponse = restConnector.sendGetRequest(transactionServiceUrl,MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, new HttpHeaders()).getBody();

        JSONParser parser = new JSONParser();

        JSONArray json = (JSONArray) parser.parse(apiResponse);


        responseMap.put("transactions", json);

        return responseMap;

    }
}

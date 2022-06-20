package com.chika.accountservice.event.listener;


import com.chika.accountservice.data.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.chika.accountservice.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ContextEventListener {

    private final CustomerRepository customerRepository;

    @EventListener
    public void handleContextStartEvent(ContextStartedEvent ctxStartEvt) {
        System.out.println("Initializing customers table");

        List<Customer> customers = new ArrayList<>();

        Customer customer1 = new Customer("Customer_1", "Customer_1_lastname","customer_1@email.com", "cust1 address", "000333449493", 1001);
        customers.add(customer1);
        Customer customer2 = new Customer("Customer_2", "Customer_2_lastname","customer_2@email.com", "cust2 address", "768949039384", 1002);
        customers.add(customer2);
        Customer customer3 = new Customer("Customer_3", "Customer_3_lastname","customer_3@email.com", "cust3 address", "453344210988", 1003);
        customers.add(customer3);

        customerRepository.saveAll(customers);

    }
}

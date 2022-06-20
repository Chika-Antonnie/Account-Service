package com.chika.accountservice.util.converters;

import com.chika.accountservice.data.dto.CustomerDto;
import com.chika.accountservice.data.entity.Customer;
import org.springframework.beans.BeanUtils;

public class CustomerConverter extends BaseConverter<Customer, CustomerDto>{

    AccountConverter accountConverter = new AccountConverter();

    @Override
    public Customer convertToEntity(CustomerDto dto, Object... args) {
        Customer entity = new Customer();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity, "accounts");
        }
        return entity;
    }

    @Override
    public CustomerDto convertToDto(Customer entity, Object... args) {
        CustomerDto dto = new CustomerDto();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto, "accounts");
        }
        return dto;
    }
}

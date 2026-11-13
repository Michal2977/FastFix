package com.fastfix.mapper;

import com.fastfix.dto.CustomerDto;
import com.fastfix.entity.Customer;

public class CustomerMapper {
    public static CustomerDto customerDto(Customer customer){
        return new CustomerDto(customer.getCustomerFirstName(),customer.getCustomerLastName(),customer.getCustomerEmail(),
                customer.getCustomerPhoneNumber());
    }
}

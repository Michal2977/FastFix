package com.fastfix.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {


    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
    private String customerPhoneNumber;


    public CustomerDto() {
    }


    public CustomerDto(String customerFirstName, String customerLastName, String customerEmail, String customerPhoneNumber) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerEmail = customerEmail;
        this.customerPhoneNumber = customerPhoneNumber;
    }
}

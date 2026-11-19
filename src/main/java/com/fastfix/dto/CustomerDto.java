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
    private String customerPickupPerson;


    public CustomerDto() {
    }

    public CustomerDto(String customerFirstName, String customerLastName, String customerEmail,
                       String customerPhoneNumber, String customerPickupPerson) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerEmail = customerEmail;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerPickupPerson = customerPickupPerson;
    }
}

package com.fastfix.request;


import lombok.Getter;

@Getter
public class RegisterAnOrderRequest {

    private String deviceType;
    private String model;
    private String serialNumber;
    private int amount;
    private String manufacturer;
    private String customerFirstName;
    private String customerLastName;
    private String customerPhoneNumber;
    private String customerEmail;


    public RegisterAnOrderRequest() {
    }
}

package com.fastfix.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_first_name")
    private String customerFirstName;

    @Column(name = "customer_last_name")
    private String customerLastName;

    @Column(name = "customer_phone_number")
    private String customerPhoneNumber;

    @Column(name = "customer_email")
    private String customerEmail;

    @OneToMany(fetch = FetchType.LAZY ,mappedBy = "customer")
    private List<DeviceIntake> deviceIntake = new ArrayList<>();


    public Customer() {
    }

    public Customer(String customerFirstName, String customerLastName, String customerPhoneNumber,
                    String customerEmail, List<DeviceIntake> deviceIntake) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerEmail = customerEmail;
        this.deviceIntake = deviceIntake;
    }
}

package com.fastfix.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Column(name = "customer_first_name",nullable = false)
    @Size(min = 2,max = 30)
    private String customerFirstName;

    @Column(name = "customer_last_name")
    @Size(max = 30)
    private String customerLastName;

    @Column(name = "customer_email")
    @Size(max = 100)
    private String customerEmail;

    @Column(name = "customer_phone_number")
    @Size(max = 20)
    private String customerPhoneNumber;

    @Column(name = "customer_pickup_person")
    @Size(max = 30)
    private String customerPickupPerson;

    @OneToMany(fetch = FetchType.LAZY ,mappedBy = "customer")
    private List<DeviceIntake> deviceIntake = new ArrayList<>();

    public Customer() {
    }

    public Customer(String customerFirstName, String customerLastName, String customerEmail,
                    String customerPhoneNumber, String customerPickupPerson, List<DeviceIntake> deviceIntake) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerEmail = customerEmail;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerPickupPerson = customerPickupPerson;
        this.deviceIntake = deviceIntake;
    }
}

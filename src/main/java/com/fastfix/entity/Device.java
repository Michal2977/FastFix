package com.fastfix.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(name = "device_type")
    private String deviceType;

    @Size(max = 50)
    @Column(name = "model")
    private String model;

    @Size(max = 50)
    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "amount")
    @Min(1)
    private int amount;

    @Size(max = 50)
    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "problem_description",columnDefinition = "TEXT" ,nullable = false)
    @Size(max = 1000,min = 20)
    private String problemDescription;

    @Column(name = "device_condition",nullable = false)
    @Size(min = 10 ,max = 150)
    private String deviceCondition;

    @Column(name = "accessories")
    @Size(max = 200)
    private String accessories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_intake_id")
    private DeviceIntake deviceIntake;

    public Device() {
    }

    public Device(String deviceType, String model, String serialNumber, int amount, String manufacturer,
                  String problemDescription, String deviceCondition, String accessories, DeviceIntake deviceIntake) {
        this.deviceType = deviceType;
        this.model = model;
        this.serialNumber = serialNumber;
        this.amount = amount;
        this.manufacturer = manufacturer;
        this.problemDescription = problemDescription;
        this.deviceCondition = deviceCondition;
        this.accessories = accessories;
        this.deviceIntake = deviceIntake;
    }
}

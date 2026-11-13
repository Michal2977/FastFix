package com.fastfix.entity;

import jakarta.persistence.*;
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

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "model")
    private String model;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "amount")
    private int amount;

    @Column(name = "manufacturer")
    private String manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_intake_id")
    private DeviceIntake deviceIntake;

    public Device() {
    }

    public Device(String deviceType, String model, String serialNumber, int amount, String manufacturer, DeviceIntake deviceIntake) {
        this.deviceType = deviceType;
        this.model = model;
        this.serialNumber = serialNumber;
        this.amount = amount;
        this.manufacturer = manufacturer;
        this.deviceIntake = deviceIntake;
    }
}

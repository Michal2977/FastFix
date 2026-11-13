package com.fastfix.entity;


import com.fastfix.enums.DeviceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "device_intake")
public class DeviceIntake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_at")
    private LocalDateTime createAt;

    @Column(name = "device_status")
    @Enumerated(EnumType.STRING)
    private DeviceStatus deviceStatus;

    @OneToMany(mappedBy = "deviceIntake",cascade = CascadeType.PERSIST)
    private List<Device> device = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public DeviceIntake() {
    }

    public DeviceIntake(LocalDateTime createAt, DeviceStatus deviceStatus, List<Device> device, Customer customer, User user) {
        this.createAt = createAt;
        this.deviceStatus = deviceStatus;
        this.device = device;
        this.customer = customer;
        this.user = user;
    }
}

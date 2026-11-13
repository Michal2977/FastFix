package com.fastfix.dto;

import com.fastfix.entity.DeviceIntake;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceDto {

    private Long id;
    private String deviceType;
    private String model;
    private String serialNumber;
    private String manuFacture;
    private int amount;
    private DeviceIntakeDto deviceIntakeDto;


    public DeviceDto() {
    }

    public DeviceDto(Long id, String deviceType, String model, String serialNumber,
                     String manuFacture, int amount, DeviceIntakeDto deviceIntakeDto) {
        this.id = id;
        this.deviceType = deviceType;
        this.model = model;
        this.serialNumber = serialNumber;
        this.manuFacture = manuFacture;
        this.amount = amount;
        this.deviceIntakeDto = deviceIntakeDto;
    }
}


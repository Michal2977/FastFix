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
    private Long deviceIntakeId;
    private String problemDescription;
    private String deviceCondition;
    private String accessories;


    public DeviceDto() {
    }

    public DeviceDto(Long id, String deviceType, String model, String serialNumber, String manuFacture,
                     int amount, Long deviceIntakeId, String problemDescription, String deviceCondition, String accessories) {
        this.id = id;
        this.deviceType = deviceType;
        this.model = model;
        this.serialNumber = serialNumber;
        this.manuFacture = manuFacture;
        this.amount = amount;
        this.deviceIntakeId = deviceIntakeId;
        this.problemDescription = problemDescription;
        this.deviceCondition = deviceCondition;
        this.accessories = accessories;
    }
}


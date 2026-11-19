package com.fastfix.mapper;

import com.fastfix.dto.DeviceIntakeDto;
import com.fastfix.entity.DeviceIntake;

import java.util.stream.Collectors;

public class DeviceIntakeMapper {
    public static DeviceIntakeDto deviceIntakeDto(DeviceIntake deviceIntake){

        return new DeviceIntakeDto(deviceIntake.getId(),deviceIntake.getCreateAt(),
                deviceIntake.getDeviceStatus(),deviceIntake.getDevice().stream().map(DeviceMapper:: deviceDto)
                .collect(Collectors.toList()), CustomerMapper.customerDto(deviceIntake.getCustomer()),
                UserMapper.userDto(deviceIntake.getUser()));
    }
}

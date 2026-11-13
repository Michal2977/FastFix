package com.fastfix.mapper;

import com.fastfix.dto.DeviceDto;
import com.fastfix.dto.DeviceIntakeDto;
import com.fastfix.entity.Device;
import com.fastfix.entity.DeviceIntakeMapper;

public class DeviceMapper {
    public static DeviceDto deviceDto(Device device){


        return new DeviceDto(device.getId(),device.getDeviceType(),device.getModel(),device.getSerialNumber()
        ,device.getManufacturer(),device.getAmount(), DeviceIntakeMapper.deviceIntakeDto(device.getDeviceIntake()));
    }
}

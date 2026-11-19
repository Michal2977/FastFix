package com.fastfix.mapper;

import com.fastfix.dto.DeviceDto;
import com.fastfix.entity.Device;

public class DeviceMapper {
    public static DeviceDto deviceDto(Device device){

        return new DeviceDto(device.getId(),device.getDeviceType(),device.getModel(),device.getSerialNumber()
        ,device.getManufacturer(),device.getAmount(),device.getDeviceIntake().getId());
    }
}

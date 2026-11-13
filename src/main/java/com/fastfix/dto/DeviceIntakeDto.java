package com.fastfix.dto;

import com.fastfix.enums.DeviceStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DeviceIntakeDto {

    private Long id;
    private LocalDateTime createAt;
    private DeviceStatus deviceStatus;
    private List<DeviceDto> deviceDto;
    private CustomerDto customerDto;
    private UserDto userDto;

    public DeviceIntakeDto() {
    }

    public DeviceIntakeDto(Long id, LocalDateTime createAt, DeviceStatus deviceStatus,
                           List<DeviceDto> deviceDto, CustomerDto customerDto, UserDto userDto) {
        this.id = id;
        this.createAt = createAt;
        this.deviceStatus = deviceStatus;
        this.deviceDto = deviceDto;
        this.customerDto = customerDto;
        this.userDto = userDto;
    }
}

package com.fastfix.service.Impl;

import com.fastfix.dto.DeviceIntakeDto;
import com.fastfix.entity.*;
import com.fastfix.enums.DeviceStatus;
import com.fastfix.repository.DeviceIntakeRepository;
import com.fastfix.request.RegisterAnOrderRequest;
import com.fastfix.service.AuthService;
import com.fastfix.service.DeviceIntakeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class DeviceIntakeImpl implements DeviceIntakeService {

    private final AuthServiceImpl authService;
    private final DeviceIntakeRepository deviceIntakeRepository;

    public DeviceIntakeImpl(AuthServiceImpl authService, DeviceIntakeRepository deviceIntakeRepository) {
        this.authService = authService;
        this.deviceIntakeRepository = deviceIntakeRepository;
    }

    @Transactional
    public DeviceIntakeDto registerAnOrder(String email, RegisterAnOrderRequest request){

        User user = authService.findByEmail(email);

        DeviceIntake deviceIntake = new DeviceIntake();
        deviceIntake.setCreateAt(LocalDateTime.now());
        deviceIntake.setDeviceStatus(DeviceStatus.ACCEPTED);

        Device device = new Device();
        device.setDeviceType(request.getDeviceType());
        device.setModel(request.getModel());
        device.setSerialNumber(request.getSerialNumber());
        device.setManufacturer(request.getManufacturer());
        device.setAmount(request.getAmount());
        device.setDeviceIntake(deviceIntake);

        deviceIntake.getDevice().add(device);

        Customer customer = new Customer();
        customer.setCustomerFirstName(request.getCustomerFirstName());
        customer.setCustomerLastName(request.getCustomerLastName());
        customer.setCustomerPhoneNumber(request.getCustomerPhoneNumber());
        customer.setCustomerEmail(request.getCustomerEmail());
        customer.getDeviceIntake().add(deviceIntake);
        deviceIntake.setCustomer(customer);
        deviceIntake.setUser(user);

        deviceIntakeRepository.save(deviceIntake);
        return DeviceIntakeMapper.deviceIntakeDto(deviceIntake);

    }



}

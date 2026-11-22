package com.fastfix.service.Impl;

import com.fastfix.dto.DeviceIntakeDto;
import com.fastfix.entity.*;
import com.fastfix.enums.DeviceStatus;
import com.fastfix.mapper.DeviceIntakeMapper;
import com.fastfix.repository.CustomerRepository;
import com.fastfix.repository.DeviceIntakeRepository;
import com.fastfix.request.RegisterAnOrderRequest;
import com.fastfix.service.DeviceIntakeService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class DeviceIntakeImpl implements DeviceIntakeService {

    private final AuthServiceImpl authService;
    private final DeviceIntakeRepository deviceIntakeRepository;
    private final CustomerRepository customerRepository;
    private final EmailServiceImpl emailService;

    public DeviceIntakeImpl(AuthServiceImpl authService, DeviceIntakeRepository deviceIntakeRepository, CustomerRepository customerRepository, EmailServiceImpl emailService) {
        this.authService = authService;
        this.deviceIntakeRepository = deviceIntakeRepository;

        this.customerRepository = customerRepository;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST','MANAGER')")
    public DeviceIntakeDto registerAnOrder(String email, RegisterAnOrderRequest request,String siteUrl) throws MessagingException {

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
        device.setProblemDescription(request.getProblemDescription());
        device.setDeviceCondition(request.getDeviceCondition());
        device.setAccessories(request.getAccessories());

        deviceIntake.getDevice().add(device);

        Customer customer = new Customer();
        customer.setCustomerEmail(request.getCustomerEmail());
        customer.setCustomerPhoneNumber(request.getCustomerPhoneNumber());
        customer.setCustomerPickupPerson(request.getCustomerPickupPerson());
        customer.setCustomerFirstName(request.getCustomerFirstName());
        customer.setCustomerLastName(request.getCustomerLastName());
        customer.getDeviceIntake().add(deviceIntake);

        deviceIntake.setCustomer(customer);
        deviceIntake.setUser(user);
        deviceIntakeRepository.save(deviceIntake);

        if (request.getCustomerEmail() != null && !request.getCustomerEmail().isBlank()){
            emailService.warmNotification(customer,siteUrl);
        }

        return DeviceIntakeMapper.deviceIntakeDto(deviceIntake);

    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','RECEPTIONIST','TECHNICIAN')")
    public List<DeviceIntakeDto>  findAllIntakeOrders(){
        List<DeviceIntake> deviceIntakes = deviceIntakeRepository.findAllWithDevice();
        return deviceIntakes.stream().map(DeviceIntakeMapper :: deviceIntakeDto).toList();
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','RECEPTIONIST','TECHNICIAN')")
    public List<DeviceIntakeDto> findAllAcceptedOrders(){
     List<DeviceIntake> deviceIntakesAccepted = deviceIntakeRepository.findAllDeviceIntakeAccepted();
     return deviceIntakesAccepted.stream().map(DeviceIntakeMapper :: deviceIntakeDto).toList();
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','RECEPTIONIST','TECHNICIAN')")
    public List<DeviceIntakeDto> findAllInProgressOrders() {
        List<DeviceIntake> deviceIntakes = deviceIntakeRepository.findAllDeviceIntakeInProgress();
        return deviceIntakes.stream().map(DeviceIntakeMapper :: deviceIntakeDto).toList();
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','RECEPTIONIST','TECHNICIAN')")
    public List<DeviceIntakeDto> findAllCompletedOrders() {
        List<DeviceIntake> deviceIntakes = deviceIntakeRepository.findAllDeviceIntakeCompleted();
        return deviceIntakes.stream().map(DeviceIntakeMapper :: deviceIntakeDto).toList();
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public long countAllAcceptedOrders(){
        return deviceIntakeRepository.countAcceptedOrders();
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public long countAllInProgressOrders() {
        return deviceIntakeRepository.countInProgressOrders();
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public long countAllCompletedOrders() {
        return deviceIntakeRepository.countCompletedOrders();
    }

    @Override
    public long countAllOrders() {
        return deviceIntakeRepository.countAllOrders();
    }

}



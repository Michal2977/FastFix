package com.fastfix.service;

import com.fastfix.dto.DeviceIntakeDto;
import com.fastfix.request.RegisterAnOrderRequest;
import jakarta.mail.MessagingException;

import java.util.List;

public interface DeviceIntakeService {
    DeviceIntakeDto registerAnOrder(String email, RegisterAnOrderRequest request,String siteUrl) throws MessagingException;
    List<DeviceIntakeDto> findAllIntakeOrders();
    List<DeviceIntakeDto> findAllAcceptedOrders();
    List<DeviceIntakeDto> findAllInProgressOrders();
    List<DeviceIntakeDto> findAllCompletedOrders();
    long countAllAcceptedOrders();
    long countAllInProgressOrders();
    long countAllCompletedOrders();
    long countAllOrders();
}

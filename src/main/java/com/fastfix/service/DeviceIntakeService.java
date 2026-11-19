package com.fastfix.service;

import com.fastfix.dto.DeviceIntakeDto;
import com.fastfix.request.RegisterAnOrderRequest;
import jakarta.mail.MessagingException;

public interface DeviceIntakeService {
    DeviceIntakeDto registerAnOrder(String email, RegisterAnOrderRequest request,String siteUrl) throws MessagingException;
}

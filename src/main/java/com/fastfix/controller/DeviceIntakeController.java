package com.fastfix.controller;

import com.fastfix.request.RegisterAnOrderRequest;
import com.fastfix.response.ApiResponse;
import com.fastfix.service.Impl.DeviceIntakeImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DeviceIntakeController {

    private final DeviceIntakeImpl deviceIntake;

    public DeviceIntakeController(DeviceIntakeImpl deviceIntake) {
        this.deviceIntake = deviceIntake;
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createAnOrder(Authentication authentication,@RequestBody RegisterAnOrderRequest request){

        deviceIntake.registerAnOrder(authentication.getName(),request);
        return ResponseEntity.ok().body(new ApiResponse("The order has been accepted"));
    }
}

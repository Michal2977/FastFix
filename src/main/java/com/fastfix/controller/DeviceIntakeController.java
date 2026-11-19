package com.fastfix.controller;

import com.fastfix.Util.Utility;
import com.fastfix.request.RegisterAnOrderRequest;
import com.fastfix.response.ApiResponse;
import com.fastfix.service.Impl.DeviceIntakeImpl;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<?> createAnOrder(Authentication authentication, @RequestBody RegisterAnOrderRequest request,
                                           HttpServletRequest requests){
        try {
            String siteUrl = Utility.servletRequest(requests);
            deviceIntake.registerAnOrder(authentication.getName(),request,siteUrl);
            return ResponseEntity.ok().body(new ApiResponse("The order has been accepted"));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new ApiResponse("Something Went Wrong Try again Later"));
        }

    }
}

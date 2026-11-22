package com.fastfix.controller;

import com.fastfix.Util.Utility;
import com.fastfix.request.RegisterAnOrderRequest;
import com.fastfix.response.ApiResponse;
import com.fastfix.service.Impl.DeviceIntakeImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/all-orders")
    public ResponseEntity<?> findAllIntakeOrders(){
        return ResponseEntity.ok().body(deviceIntake.findAllIntakeOrders());
    }

    @GetMapping("/accepted-orders")
    public ResponseEntity<?> findAllAcceptedOrders(){
        return ResponseEntity.ok().body(deviceIntake.findAllAcceptedOrders());
    }
    @GetMapping("/in-progress-orders")
    public ResponseEntity<?> findAllInProgressOrders(){
        return ResponseEntity.ok().body(deviceIntake.findAllInProgressOrders());
    }
    @GetMapping("/completed-orders")
    public ResponseEntity<?> findAllCompletedOrders(){
        return ResponseEntity.ok().body(deviceIntake.findAllCompletedOrders());
    }

    @GetMapping("/count-accepted")
    public ResponseEntity<Long> countAllAcceptedOrders(){
        return ResponseEntity.ok().body(deviceIntake.countAllAcceptedOrders());
    }
    @GetMapping("/count-in-progress")
    public ResponseEntity<Long> countAllInProgressOrders(){
        return ResponseEntity.ok().body(deviceIntake.countAllInProgressOrders());
    }
    @GetMapping("/count-completed")
    public ResponseEntity<Long> countAllCompletedOrders(){
        return ResponseEntity.ok().body(deviceIntake.countAllCompletedOrders());
    }
    @GetMapping("/count-all")
    public ResponseEntity<Long> countAllOrders(){
        return ResponseEntity.ok().body(deviceIntake.countAllOrders());
    }
}

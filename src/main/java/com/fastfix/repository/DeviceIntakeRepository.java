package com.fastfix.repository;

import com.fastfix.entity.DeviceIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceIntakeRepository extends JpaRepository<DeviceIntake,Long> {


    @Query("SELECT d FROM DeviceIntake d JOIN FETCH d.device  ORDER BY d.createAt DESC ")
    List<DeviceIntake> findAllWithDevice();

    @Query("SELECT d FROM DeviceIntake d JOIN FETCH d.device JOIN FETCH d.customer WHERE d.deviceStatus = 'ACCEPTED' " +
            " ORDER BY d.createAt DESC ")
    List<DeviceIntake> findAllDeviceIntakeAccepted();

    @Query("SELECT d FROM DeviceIntake d JOIN FETCH d.device JOIN FETCH d.customer WHERE d.deviceStatus = 'IN_PROGRESS'" +
            "ORDER BY d.createAt DESC")
    List<DeviceIntake> findAllDeviceIntakeInProgress();

    @Query("SELECT d FROM DeviceIntake d JOIN FETCH d.device JOIN FETCH d.customer WHERE d.deviceStatus = 'COMPLETED'" +
            "ORDER BY d.createAt DESC")
    List<DeviceIntake> findAllDeviceIntakeCompleted();

    @Query("SELECT COUNT(d) FROM DeviceIntake d WHERE d.deviceStatus = 'ACCEPTED'")
    long countAcceptedOrders();

    @Query("SELECT COUNT(d) FROM DeviceIntake d WHERE d.deviceStatus = 'IN_PROGRESS'")
    long countInProgressOrders();

    @Query("SELECT COUNT(d) FROM DeviceIntake d WHERE d.deviceStatus = 'COMPLETED'")
    long countCompletedOrders();

    @Query("SELECT COUNT(d) FROM DeviceIntake d WHERE d.deviceStatus IN ('COMPLETED','ACCEPTED','IN_PROGRESS')")
    long countAllOrders();

}

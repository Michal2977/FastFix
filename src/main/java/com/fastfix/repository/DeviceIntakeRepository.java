package com.fastfix.repository;

import com.fastfix.entity.DeviceIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceIntakeRepository extends JpaRepository<DeviceIntake,Long> {
}

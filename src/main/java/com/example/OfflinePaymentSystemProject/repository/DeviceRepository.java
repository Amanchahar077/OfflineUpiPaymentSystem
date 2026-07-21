package com.example.OfflinePaymentSystemProject.repository;

import com.example.OfflinePaymentSystemProject.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device,Long> {

    Optional<Device> findByDeviceId(String deviceId);

}

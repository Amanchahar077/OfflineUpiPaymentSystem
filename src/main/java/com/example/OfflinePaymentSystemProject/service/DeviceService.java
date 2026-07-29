package com.example.OfflinePaymentSystemProject.service;

import com.example.OfflinePaymentSystemProject.entity.Device;
import com.example.OfflinePaymentSystemProject.repository.DeviceRepository;
import com.example.OfflinePaymentSystemProject.offline.crypto.RSAUtil;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {

        this.deviceRepository = deviceRepository;

    }

    public Device registerDevice(Device device)
            throws Exception {

        KeyPair pair =
                RSAUtil.generateKeyPair();

        device.setPublicKey(
                RSAUtil.encodePublicKey(
                        pair.getPublic()));

        device.setPrivateKey(
                RSAUtil.encodePrivateKey(
                        pair.getPrivate()));

        return deviceRepository.save(device);

    }

}

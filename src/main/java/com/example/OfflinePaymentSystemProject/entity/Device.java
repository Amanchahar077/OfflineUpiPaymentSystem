package com.example.OfflinePaymentSystemProject.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deviceId;
    private String ownerUpiId;
    private String online;

    public Device(Long id, String deviceId, String ownerUpiId, String online) {
        this.id = id;
        this.deviceId = deviceId;
        this.ownerUpiId = ownerUpiId;
        this.online = online;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOwnerUpiId() {
        return ownerUpiId;
    }

    public void setOwnerUpiId(String ownerUpiId) {
        this.ownerUpiId = ownerUpiId;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}

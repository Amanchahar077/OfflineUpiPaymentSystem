package com.example.OfflinePaymentSystemProject.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String deviceId;

    @Column(nullable = false)
    private String ownerUpiId;

    private boolean online;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String publicKey;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String privateKey;

    public Device() {
    }

    public Device(Long id, String deviceId, String ownerUpiId, boolean online, String publicKey, String privateKey) {
        this.id = id;
        this.deviceId = deviceId;
        this.ownerUpiId = ownerUpiId;
        this.online = online;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
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

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
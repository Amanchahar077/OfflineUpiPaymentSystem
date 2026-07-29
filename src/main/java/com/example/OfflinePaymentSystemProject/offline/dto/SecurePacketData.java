package com.example.OfflinePaymentSystemProject.offline.dto;

public class SecurePacketData {

    private String encryptedPayload;

    private String encryptedAESKey;

    private String digitalSignature;

    public SecurePacketData() {
    }

    public SecurePacketData(String encryptedPayload,
                            String encryptedAESKey,
                            String digitalSignature) {
        this.encryptedPayload = encryptedPayload;
        this.encryptedAESKey = encryptedAESKey;
        this.digitalSignature = digitalSignature;
    }

    public String getEncryptedPayload() {
        return encryptedPayload;
    }

    public void setEncryptedPayload(String encryptedPayload) {
        this.encryptedPayload = encryptedPayload;
    }

    public String getEncryptedAESKey() {
        return encryptedAESKey;
    }

    public void setEncryptedAESKey(String encryptedAESKey) {
        this.encryptedAESKey = encryptedAESKey;
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }
}
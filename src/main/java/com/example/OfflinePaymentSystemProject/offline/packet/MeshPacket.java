package com.example.OfflinePaymentSystemProject.offline.packet;

import java.util.List;

public class MeshPacket {

    private String packetId;

    private String encryptedPayload;

    private String encryptedAESKey;

    private String digitalSignature;

    private List<String> route;

    private int currentHop;

    public MeshPacket(String packetId, String encryptedPayload, String encryptedAESKey, String digitalSignature, List<String> route, int currentHop) {
        this.packetId = packetId;
        this.encryptedPayload = encryptedPayload;
        this.encryptedAESKey = encryptedAESKey;
        this.digitalSignature = digitalSignature;
        this.route = route;
        this.currentHop = currentHop;
    }

    public MeshPacket() {}

    public String getPacketId() {
        return packetId;
    }

    public void setPacketId(String packetId) {
        this.packetId = packetId;
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

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }

    public int getCurrentHop() {
        return currentHop;
    }

    public void setCurrentHop(int currentHop) {
        this.currentHop = currentHop;
    }
}

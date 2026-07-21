package com.example.OfflinePaymentSystemProject.offline.packet;

import java.util.List;

public class MeshPacket {

    private String packetId;

    private String senderUpiId;

    private String receiverUpiId;

    private Double amount;

    private List<String> route;

    private int currentHop;

    public MeshPacket(String packetId, String senderUpiId, String receiverUpiId, Double amount, List<String> route, int currentHop) {
        this.packetId = packetId;
        this.senderUpiId = senderUpiId;
        this.receiverUpiId = receiverUpiId;
        this.amount = amount;
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

    public String getSenderUpiId() {
        return senderUpiId;
    }

    public void setSenderUpiId(String senderUpiId) {
        this.senderUpiId = senderUpiId;
    }

    public String getReceiverUpiId() {
        return receiverUpiId;
    }

    public void setReceiverUpiId(String receiverUpiId) {
        this.receiverUpiId = receiverUpiId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

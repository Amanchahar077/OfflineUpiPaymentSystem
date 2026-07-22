package com.example.OfflinePaymentSystemProject.dto;

public class OfflinePaymentRequestDTO {

    private String senderDeviceId;

    private String receiverDeviceId;

    private Double amount;

    public OfflinePaymentRequestDTO() {
    }

    public String getSenderDeviceId() {
        return senderDeviceId;
    }

    public void setSenderDeviceId(String senderDeviceId) {
        this.senderDeviceId = senderDeviceId;
    }

    public String getReceiverDeviceId() {
        return receiverDeviceId;
    }

    public void setReceiverDeviceId(String receiverDeviceId) {
        this.receiverDeviceId = receiverDeviceId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

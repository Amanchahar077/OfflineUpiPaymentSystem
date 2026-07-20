package com.example.OfflinePaymentSystemProject.dto;

public class CreatePaymentResponseDTO {

    private String transactionId;
    private String message;
    private Double senderBalance;
    private Double receiverBalance;

    public CreatePaymentResponseDTO(String transactionId, String message, Double senderBalance, Double receiverBalance) {
        this.transactionId = transactionId;
        this.message = message;
        this.senderBalance = senderBalance;
        this.receiverBalance = receiverBalance;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getSenderBalance() {
        return senderBalance;
    }

    public void setSenderBalance(Double senderBalance) {
        this.senderBalance = senderBalance;
    }

    public Double getReceiverBalance() {
        return receiverBalance;
    }

    public void setReceiverBalance(Double receiverBalance) {
        this.receiverBalance = receiverBalance;
    }
}

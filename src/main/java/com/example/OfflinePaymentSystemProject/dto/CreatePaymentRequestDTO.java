package com.example.OfflinePaymentSystemProject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreatePaymentRequestDTO {

    @NotBlank
    private String senderUpiId;

    @NotBlank
    private String receiverUpiId;

    @Min(1)
    private Double amount;

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
}

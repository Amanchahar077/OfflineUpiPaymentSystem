package com.example.OfflinePaymentSystemProject.controller;

import com.example.OfflinePaymentSystemProject.dto.CreatePaymentRequestDTO;
import com.example.OfflinePaymentSystemProject.dto.CreatePaymentResponseDTO;
import com.example.OfflinePaymentSystemProject.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<CreatePaymentResponseDTO> transfer(
        @Valid @RequestBody CreatePaymentRequestDTO request){

        return ResponseEntity.ok(
                paymentService.transfer(request));
    }


}

package com.example.OfflinePaymentSystemProject.controller;


import com.example.OfflinePaymentSystemProject.dto.OfflinePaymentRequestDTO;
import com.example.OfflinePaymentSystemProject.service.OfflinePaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/offline-payments")
public class OfflinePaymentController {

    private final OfflinePaymentService offlinePaymentService;

    public OfflinePaymentController(OfflinePaymentService offlinePaymentService) {
        this.offlinePaymentService = offlinePaymentService;
    }

    @PostMapping
    public ResponseEntity<String> makeOfflinePayment(
            @RequestBody OfflinePaymentRequestDTO request) {

        offlinePaymentService.processOfflinePayment(request);

        return ResponseEntity.ok("Offline payment processed successfully.");
    }
}

package com.example.OfflinePaymentSystemProject.service;

import com.example.OfflinePaymentSystemProject.offline.routing.RoutingService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class OfflinePaymentService {

    private final RoutingService routingService;

    @Autowired
    public OfflinePaymentService(RoutingService routingService) {
        this.routingService = routingService;
    }

    public void sendOfflinePayment(){

    }

}

package com.example.OfflinePaymentSystemProject.offline.routing;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutingService {

    public List<String> findRoute(String sender, String receiver){
        return List.of(sender,receiver);
    }

}

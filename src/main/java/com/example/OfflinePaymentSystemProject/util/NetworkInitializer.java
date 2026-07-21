package com.example.OfflinePaymentSystemProject.util;

import com.example.OfflinePaymentSystemProject.entity.Account;
import com.example.OfflinePaymentSystemProject.offline.mesh.MeshNetwork;
import com.example.OfflinePaymentSystemProject.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NetworkInitializer implements CommandLineRunner {

    private final MeshNetwork meshNetwork;
    private final AccountRepository accountRepository;

    public NetworkInitializer(MeshNetwork meshNetwork, AccountRepository accountRepository) {
        this.meshNetwork = meshNetwork;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) {
        // 1. Register the devices into the mesh network
        meshNetwork.registerDevice("device-1", false);
        meshNetwork.registerDevice("device-2", false);
        meshNetwork.registerDevice("device-3", true);

        // 2. Build the connections (edges) between them
        meshNetwork.connectDevices("device-1", "device-2");
        meshNetwork.connectDevices("device-2", "device-3");

        // 3. Seed the accounts in the database
        Account sender = new Account();
        sender.setName("Sender Name");
        sender.setUpiId("sender@upi");
        sender.setBalance(1000.0);
        accountRepository.save(sender);

        Account receiver = new Account();
        receiver.setName("Receiver Name");
        receiver.setUpiId("receiver@upi");
        receiver.setBalance(500.0);
        accountRepository.save(receiver);

        System.out.println("Mesh network and database accounts successfully initialized!");
    }
}

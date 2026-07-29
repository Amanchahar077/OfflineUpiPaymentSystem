package com.example.OfflinePaymentSystemProject.service;

import com.example.OfflinePaymentSystemProject.dto.CreatePaymentRequestDTO;
import com.example.OfflinePaymentSystemProject.dto.CreatePaymentResponseDTO;
import com.example.OfflinePaymentSystemProject.dto.OfflinePaymentRequestDTO;
import com.example.OfflinePaymentSystemProject.entity.Device;
import com.example.OfflinePaymentSystemProject.offline.mesh.MeshNetwork;
import com.example.OfflinePaymentSystemProject.offline.mesh.MeshNode;
import com.example.OfflinePaymentSystemProject.offline.packet.MeshPacket;
import com.example.OfflinePaymentSystemProject.offline.routing.RoutingService;
import com.example.OfflinePaymentSystemProject.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.OfflinePaymentSystemProject.offline.crypto.CryptoService;
import com.example.OfflinePaymentSystemProject.offline.dto.SecurePacketData;

import java.util.List;
import java.util.UUID;

@Service
public class OfflinePaymentService {

    private final RoutingService routingService;
    private final MeshNetwork meshNetwork;
    private final PaymentService paymentService;
    private final DeviceRepository deviceRepository;
    private final CryptoService cryptoService;

    public OfflinePaymentService(RoutingService routingService, MeshNetwork meshNetwork, PaymentService paymentService, DeviceRepository deviceRepository, CryptoService cryptoService) {
        this.routingService = routingService;
        this.meshNetwork = meshNetwork;
        this.paymentService = paymentService;
        this.deviceRepository = deviceRepository;
        this.cryptoService = cryptoService;
    }

    


    public MeshPacket createPacket(String senderDevice,
            String receiverDevice,
            String senderUpiId,
            String receiverUpiId,
            Double amount) {

        List<String> route = routingService.findShortestPath(senderDevice, receiverDevice);

        String paymentData = senderUpiId + "," + receiverUpiId + "," + amount;

        SecurePacketData secureData;
        try {
            secureData = cryptoService.securePacket(senderDevice, receiverDevice, paymentData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to secure payment packet", e);
        }

        MeshPacket packet = new MeshPacket();

        packet.setPacketId(UUID.randomUUID().toString());
        packet.setEncryptedPayload(secureData.getEncryptedPayload());
        packet.setEncryptedAESKey(secureData.getEncryptedAESKey());
        packet.setDigitalSignature(secureData.getDigitalSignature());
        packet.setRoute(route);
        packet.setCurrentHop(0);

        return packet;
    }

    public void forwardPacket(MeshPacket packet) {

        System.out.println("\nForwarding Packet...\n");

        for (String device : packet.getRoute()) {

            System.out.println("Packet reached : " + device);

            packet.setCurrentHop(packet.getCurrentHop() + 1);

            MeshNode node = meshNetwork.getDevice(device);

            if (node.isOnline()) {

                System.out.println("\nInternet found at : " + device);
                System.out.println("\nSending packet to backend...\n");
                CreatePaymentRequestDTO request = buildPaymentRequest(packet);
                CreatePaymentResponseDTO response = paymentService.transfer(request);
                System.out.println("Transaction Successful");


                return;
            }

        }

        System.out.println("\nNo online device found.");
    }

    public void processOfflinePayment(OfflinePaymentRequestDTO request) {

        Device sender = deviceRepository
                .findByDeviceId(request.getSenderDeviceId())
                .orElseThrow(() ->
                        new RuntimeException("Sender device not found"));

        Device receiver = deviceRepository
                .findByDeviceId(request.getReceiverDeviceId())
                .orElseThrow(() ->
                        new RuntimeException("Receiver device not found"));

        MeshPacket packet = createPacket(
                sender.getDeviceId(),
                receiver.getDeviceId(),
                sender.getOwnerUpiId(),
                receiver.getOwnerUpiId(),
                request.getAmount()
        );

        forwardPacket(packet);
    }

    private CreatePaymentRequestDTO buildPaymentRequest(MeshPacket packet) {

        List<String> route = packet.getRoute();
        if (route == null || route.isEmpty()) {
            throw new RuntimeException("Invalid route in packet");
        }
        String senderDevice = route.get(0);
        String receiverDevice = route.get(route.size() - 1);

        SecurePacketData secureData = new SecurePacketData(
                packet.getEncryptedPayload(),
                packet.getEncryptedAESKey(),
                packet.getDigitalSignature()
        );

        String decryptedPayload;
        try {
            decryptedPayload = cryptoService.decryptPacket(receiverDevice, secureData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt packet", e);
        }

        try {
            boolean verified = cryptoService.verifySignature(senderDevice, decryptedPayload, packet.getDigitalSignature());
            if (!verified) {
                throw new RuntimeException("Digital signature verification failed!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify digital signature", e);
        }

        String[] parts = decryptedPayload.split(",");
        if (parts.length < 3) {
            throw new RuntimeException("Invalid payment data format in decrypted payload");
        }

        String senderUpiId = parts[0];
        String receiverUpiId = parts[1];
        Double amount = Double.parseDouble(parts[2]);

        CreatePaymentRequestDTO request = new CreatePaymentRequestDTO();

        request.setSenderUpiId(senderUpiId);
        request.setReceiverUpiId(receiverUpiId);
        request.setAmount(amount);

        return request;
    }

}

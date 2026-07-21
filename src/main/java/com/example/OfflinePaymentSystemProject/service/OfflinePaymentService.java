package com.example.OfflinePaymentSystemProject.service;

import com.example.OfflinePaymentSystemProject.dto.CreatePaymentRequestDTO;
import com.example.OfflinePaymentSystemProject.dto.CreatePaymentResponseDTO;
import com.example.OfflinePaymentSystemProject.dto.OfflinePaymentRequestDTO;
import com.example.OfflinePaymentSystemProject.offline.mesh.MeshNetwork;
import com.example.OfflinePaymentSystemProject.offline.mesh.MeshNode;
import com.example.OfflinePaymentSystemProject.offline.packet.MeshPacket;
import com.example.OfflinePaymentSystemProject.offline.routing.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OfflinePaymentService {

    private final RoutingService routingService;
    private final MeshNetwork meshNetwork;
    private final PaymentService paymentService;

    @Autowired
    public OfflinePaymentService(RoutingService routingService, MeshNetwork meshNetwork,
            PaymentService paymentService) {
        this.routingService = routingService;
        this.meshNetwork = meshNetwork;
        this.paymentService = paymentService;
    }

    public MeshPacket createPacket(String senderDevice,
            String receiverDevice,
            String senderUpiId,
            String receiverUpiId,
            Double amount) {

        List<String> route = routingService.findShortestPath(senderDevice, receiverDevice);

        MeshPacket packet = new MeshPacket();

        packet.setPacketId(UUID.randomUUID().toString());
        packet.setSenderUpiId(senderUpiId);
        packet.setReceiverUpiId(receiverUpiId);
        packet.setAmount(amount);
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

        MeshPacket packet = createPacket(
                request.getSenderDeviceId(),
                request.getReceiverDeviceId(),
                request.getSenderUpiId(),
                request.getReceiverUpiId(),
                request.getAmount());

        forwardPacket(packet);
    }

    private CreatePaymentRequestDTO buildPaymentRequest(MeshPacket packet) {

        CreatePaymentRequestDTO request = new CreatePaymentRequestDTO();

        request.setSenderUpiId(packet.getSenderUpiId());
        request.setReceiverUpiId(packet.getReceiverUpiId());
        request.setAmount(packet.getAmount());

        return request;
    }

}

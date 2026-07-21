package com.example.OfflinePaymentSystemProject.offline.mesh;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MeshNetwork {

    private final Map<String, MeshNode> devices = new HashMap<>();

    public void registerDevice(String deviceId, boolean online){
        devices.put(deviceId,new MeshNode(deviceId,online));
    }

    public void connectDevices(String firstDeviceId, String secondDeviceId){
        MeshNode first = devices.get(firstDeviceId);
        MeshNode second = devices.get(secondDeviceId);

        if(first == null || second == null){
            throw new RuntimeException("Devie not found");
        }

        first.addNeighbour(second);
        second.addNeighbour(first);

    }

    public void printNetwork() {

        for (MeshNode node : devices.values()) {

            System.out.print(node.getDeviceId() + " -> ");

            for (MeshNode neighbour : node.getNeighbours()) {
                System.out.print(neighbour.getDeviceId() + " ");
            }

            System.out.println();
        }
    }

    public MeshNode findFirstOnlineDevice(List<String> route) {

        for (String deviceId : route) {

            MeshNode node = devices.get(deviceId);

            if (node != null && node.isOnline()) {
                return node;
            }

        }

        return null;
    }

    public MeshNode getDevice(String deviceId){
        return devices.get(deviceId);
    }

    public Map<String, MeshNode> getDevices(){
        return devices;
    }


}

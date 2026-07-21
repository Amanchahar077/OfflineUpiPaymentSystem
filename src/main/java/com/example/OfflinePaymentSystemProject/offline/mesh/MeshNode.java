package com.example.OfflinePaymentSystemProject.offline.mesh;

import java.util.ArrayList;
import java.util.List;

public class MeshNode {

    private String deviceId;
    private boolean online;
    private List<MeshNode> neighbours = new ArrayList<>();

    public MeshNode(String deviceId, boolean online) {
        this.deviceId = deviceId;
        this.online = online;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public boolean isOnline() {
        return online;
    }

    public List<MeshNode> getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(MeshNode node){
        neighbours.add(node);
    }

}

package com.example.OfflinePaymentSystemProject.offline.routing;

import com.example.OfflinePaymentSystemProject.offline.mesh.MeshNetwork;
import com.example.OfflinePaymentSystemProject.offline.mesh.MeshNode;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoutingService {

    private final MeshNetwork meshNetwork;

    public RoutingService(MeshNetwork meshNetwork) {
        this.meshNetwork = meshNetwork;
    }

    public List<String> findShortestPath(String sourceId, String destinationId) {

        MeshNode source = meshNetwork.getDevice(sourceId);
        MeshNode destination = meshNetwork.getDevice(destinationId);

        if (source == null || destination == null) {
            throw new RuntimeException("Source or Destination device not found");
        }

        Queue<MeshNode> queue = new LinkedList<>();
        Set<MeshNode> visited = new HashSet<>();
        Map<MeshNode, MeshNode> parent = new HashMap<>();

        queue.add(source);
        visited.add(source);

        while (!queue.isEmpty()) {

            MeshNode current = queue.poll();

            if (current.equals(destination)) {
                break;
            }

            for (MeshNode neighbour : current.getNeighbours()) {

                if (!visited.contains(neighbour)) {

                    visited.add(neighbour);
                    parent.put(neighbour, current);
                    queue.add(neighbour);

                }
            }
        }

        List<String> path = new ArrayList<>();

        MeshNode current = destination;

        while (current != null) {

            path.add(current.getDeviceId());
            current = parent.get(current);

        }

        Collections.reverse(path);

        return path;
    }


}

package com.nhlstenden.amazonsimulatie.models.pathfinding;

import com.nhlstenden.amazonsimulatie.models.position.Position;
import com.nhlstenden.amazonsimulatie.models.position.RealPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dijkstra {

    private final List<Node> nodeList;

    public Dijkstra(List<Node> n)
    {
        this.nodeList = n;
    }

    private Node minDistance(Map<Node, Integer> dist, Map<Node, Boolean> sptSet, List<Node> nodeList) {
        int min = Integer.MAX_VALUE;
        Node min_node = null;

        for (Node node : nodeList)
            if (!sptSet.get(node) && dist.get(node) <= min) {
                min = dist.get(node);
                min_node = node;
            }
        return min_node;
    }

    public List<Position> giveShortestPath(Position startNodePosition, Node finalNode) {

        Node startNode = nodeList.get(0);

        for (Node node : nodeList)
        {
            if (node.getX() == startNodePosition.getX() && node.getY() == startNodePosition.getY() && node.getZ() == startNodePosition.getZ()) {
                startNode = node;
                break;
            }

        }

        int amountOfNodes = nodeList.size();

        // Distance from start
        Map<Node, Integer> dist = new HashMap<>();
        // Hebben we deze al geprobeerd
        Map<Node, Boolean> sptSet = new HashMap<>();
        // Per node het pad vanaf het startpunt.
        Map<Node, List<Node>> paths = new HashMap<>();

        for (Node n : nodeList) {
            dist.put(n, Integer.MAX_VALUE);
            sptSet.put(n, false);
            paths.put(n, new ArrayList<>());
        }

        dist.replace(startNode, 0);

        for (int count = 0; count < amountOfNodes - 1; count++) {

            Node u = minDistance(dist, sptSet, nodeList);
            sptSet.replace(u, true);

            for (Node node : nodeList) {

                // We lopen door alle nodes heen.
                // Kijken welke nodes geconnect zijn met u, is de afstand van u naar een willekeurig kleiner dan de huidige afstand updaten
                if (!sptSet.get(node) && u.isConnectedTo(node) && dist.get(u) != Integer.MAX_VALUE && dist.get(u) + 1 < dist.get(node)) {

                    dist.replace(node, dist.get(u) + 1);
                    List<Node> path = new ArrayList<>(paths.get(u));
                    path.add(node);
                    paths.replace(node, path);
                }
            }
        }

        List<Position> pathToFinish = new ArrayList<>();;

        for (Node node : paths.get(finalNode)){
            pathToFinish.add(node.getPosition());
        }

        return pathToFinish;
    }

    public List<Node> getNodes()
    {
        return this.nodeList;
    }
}
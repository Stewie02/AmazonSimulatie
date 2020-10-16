package com.nhlstenden.amazonsimulatie.models.PathFinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dijkstra {

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

    public List<Node> giveShortestPath(List<Node> nodeList, Node startNode, Node finalNode) {

        int amountOfVertices = nodeList.size();

        Map<Node, Integer> dist = new HashMap<>();
        Map<Node, Boolean> sptSet = new HashMap<>();
        Map<Node, List<Node>> paths = new HashMap<>();

        for (Node n : nodeList) {
            dist.put(n, Integer.MAX_VALUE);
            sptSet.put(n, false);
            paths.put(n, new ArrayList<>());
        }

        dist.replace(startNode, 0);

        for (int count = 0; count < amountOfVertices - 1; count++) {

            Node u = minDistance(dist, sptSet, nodeList);
            sptSet.replace(u, true);

            for (Node node : nodeList)
                if (!sptSet.get(node) && u.isConnectedTo(node) && dist.get(u) != Integer.MAX_VALUE && dist.get(u) + 1 < dist.get(node))
                {
                    dist.replace(node, dist.get(u) + 1);
                    List<Node> path = new ArrayList<>(paths.get(u));
                    path.add(node);
                    paths.replace(node, path);
                }
        }
        return paths.get(finalNode);
    }
}
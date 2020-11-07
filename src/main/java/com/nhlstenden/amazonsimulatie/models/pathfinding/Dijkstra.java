package com.nhlstenden.amazonsimulatie.models.pathfinding;

import com.nhlstenden.amazonsimulatie.models.position.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This function has the implementation of the Dijkstra pathfinding algorithm
 * It has a List of all the Nodes in the warehouse and when the startNode and finalNode is given,
 * it will return the shortest path
 */
public class Dijkstra {

    private final List<Node> nodeList;

    public Dijkstra(List<Node> n)
    {
        this.nodeList = n;
    }

    /**
     * Returns the Node with the shortest distance from the List of Nodes that isn't checked yet
     * @param distance A Map with the Nodes as keys and distance as value
     * @param alreadyChecked A Map with the Nodes as keys and a boolean as value based on if it's checked
     * @param nodeList A List of all the Nodes in the graph
     * @return The Node with shortest distance that isn't been checked before
     */
    private Node minDistance(Map<Node, Integer> distance, Map<Node, Boolean> alreadyChecked, List<Node> nodeList) {
        int min = Integer.MAX_VALUE;
        Node min_node = null;

        /* Iterate through all the Nodes, if the distance is smaller than the smallest and not alreadyChecked. Update the min and min_node value
            */
        for (Node node : nodeList)
            if (!alreadyChecked.get(node) && distance.get(node) <= min) {
                min = distance.get(node);
                min_node = node;
            }
        return min_node;
    }

    /**
     * Return a List of Position what is the shortest path from the startNodePosition to the final Node
     * @param startNodePosition A position which values equal the values of the start Node
     * @param finalNode The finish Node
     * @return List of Position to the finalNode
     */
    public List<Position> giveShortestPath(Position startNodePosition, Node finalNode) {

        // If the startNodePosition doesn't exist we'll take the first Node out of the List
        Node startNode = nodeList.get(0);

        // Iterate trough all the Nodes and if the Position of a Node equals the startNodePosition set it as startNode
        for (Node node : nodeList)
        {
            if (node.getX() == startNodePosition.getX() && node.getY() == startNodePosition.getY() && node.getZ() == startNodePosition.getZ()) {
                startNode = node;
                break;
            }

        }

        int amountOfNodes = nodeList.size();

        // Create three maps, one with distances, one with booleans if we already checked the Node and one with the paths
        Map<Node, Integer> distance = new HashMap<>();
        Map<Node, Boolean> alreadyChecked = new HashMap<>();
        Map<Node, List<Node>> paths = new HashMap<>();

        // Put initial values in Maps
        for (Node n : nodeList) {
            distance.put(n, Integer.MAX_VALUE);
            alreadyChecked.put(n, false);
            paths.put(n, new ArrayList<>());
        }

        // Distance from startNode to itself is always zero
        distance.replace(startNode, 0);

        for (int count = 0; count < amountOfNodes - 1; count++) {

            // Get the node with the shortest distance from the startNode which we haven't checked
            Node u = minDistance(distance, alreadyChecked, nodeList);
            // Set the boolean in the alreadyChecked map to true for this node
            alreadyChecked.replace(u, true);

            // Iterate through all nodes and if the distance from u to the startNode + the distance from u to the current Node is smaller update everything
            for (Node node : nodeList) {
                if (!alreadyChecked.get(node) && u.isConnectedTo(node) && distance.get(u) != Integer.MAX_VALUE && distance.get(u) + 1 < distance.get(node)) {
                    distance.replace(node, distance.get(u) + 1);
                    List<Node> path = new ArrayList<>(paths.get(u));
                    path.add(node);
                    paths.replace(node, path);
                }
            }
        }


        List<Position> pathToFinish = new ArrayList<>();

        // Add the Positions of the Nodes to the pathToFinish List and return it
        for (Node node : paths.get(finalNode)){
            pathToFinish.add(node.getPosition());
        }

        return pathToFinish;
    }

    /**
     * Return all the Nodes
     * @return List of all the Nodes
     */
    public List<Node> getNodes()
    {
        return this.nodeList;
    }
}
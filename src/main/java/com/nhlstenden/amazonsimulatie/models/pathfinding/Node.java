package com.nhlstenden.amazonsimulatie.models.pathfinding;

import java.util.ArrayList;
import java.util.List;

import com.nhlstenden.amazonsimulatie.models.objects.HasPosition;

/**
 * A Node is a point in the Graph
 * A Node has some neighbours which we call adjacents
 */
public class Node extends HasPosition {

    private final List<Node> adjacents;

    /**
     * Give the X-coordinate, Y-coordinate and Z-coordinate of the Node
     * @param x The X-coordinate
     * @param y The Y-coordinate
     * @param z The Z-coordinate
     */
    public Node(double x, double y, double z)
    {
        super(x, y, z);
        adjacents = new ArrayList<>();
    }

    /**
     * Add an adjacent to the Node
     * @param node The adjacent Node
     */
    public void addAdjacent(Node node)
    {
        adjacents.add(node);
    }

    /**
     * Check if the Node is a neighbour of the given Node in the parameters
     * @param node Node to check
     * @return true if the Node is an adjacent
     */
    public boolean isConnectedTo(Node node)
    {
        return adjacents.contains(node);
    }

}
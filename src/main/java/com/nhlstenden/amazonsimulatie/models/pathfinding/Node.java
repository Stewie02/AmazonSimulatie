package com.nhlstenden.amazonsimulatie.models.pathfinding;

import java.util.ArrayList;
import java.util.List;

import com.nhlstenden.amazonsimulatie.models.objects.HasPosition;

public class Node extends HasPosition {

    private final List<Node> adjacents;

    public Node(double x, double y, double z)
    {
        super(x, y, z);
        adjacents = new ArrayList<>();
    }

    public void addAdjacent(Node node)
    {
        adjacents.add(node);
    }

    public boolean isConnectedTo(Node node)
    {
        return adjacents.contains(node);
    }

}
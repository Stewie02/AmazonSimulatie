package com.nhlstenden.amazonsimulatie.models.PathFinding;

import java.util.ArrayList;
import java.util.List;

import com.nhlstenden.amazonsimulatie.models.Position;

public class Node {

    private final Position position;
    private final List<Node> adjacents;
    private String name;

    public Node(double x, double y, double z, String name)
    {
        this.name = name;
        this.position = new Position(x, y, z);
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

    @Override
    public String toString()
    {
        return this.name;
    }
}

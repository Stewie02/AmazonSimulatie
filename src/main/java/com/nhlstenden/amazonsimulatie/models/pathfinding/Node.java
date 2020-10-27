package com.nhlstenden.amazonsimulatie.models.pathfinding;

import java.util.ArrayList;
import java.util.List;

import com.nhlstenden.amazonsimulatie.models.objects.interfaces.HasPosition;
import com.nhlstenden.amazonsimulatie.models.Position;

public class Node implements HasPosition {

    private final Position position;
    private final List<Node> adjacents;

    public Node(double x, double y, double z)
    {
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
    public Position getPosition() {
        return position;
    }
}
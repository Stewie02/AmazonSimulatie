package com.nhlstenden.amazonsimulatie.models.PathFinding;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.nhlstenden.amazonsimulatie.models.HasPosition;
import com.nhlstenden.amazonsimulatie.models.Position;
import com.nhlstenden.amazonsimulatie.models.Rack;

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
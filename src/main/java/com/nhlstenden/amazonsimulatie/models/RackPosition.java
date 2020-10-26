package com.nhlstenden.amazonsimulatie.models;

import com.nhlstenden.amazonsimulatie.models.pathfinding.Node;

import java.util.UUID;

public class RackPosition implements CanHoldRacks, HasPosition {

    private final Position position;
    private Rack rack = null;
    private final UUID uuid;
    private Node adjacentNode = null;

    public RackPosition(double x, double y, double z, Rack rack) {
        this(x, y, z);
        this.rack = rack;
    }

    public RackPosition(double x, double y, double z) {
        this.position = new Position(x, y, z);
        this.uuid = UUID.randomUUID();
    }

    public Position getPosition() {
        return position;
    }

    public Rack getRack() {
        return rack;
    }

    public String getUUID()
    {
        return this.uuid.toString();
    }

    public void setRack(Rack rack) {
        this.rack = rack;
        if (rack != null)
            rack.setPosition(position.x, position.y, position.z);
    }

    public Node getAdjacentNode() {
        return adjacentNode;
    }

    public void setAdjacentNode(Node adjacentNode) {
        this.adjacentNode = adjacentNode;
    }

}

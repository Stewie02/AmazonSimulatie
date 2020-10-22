package com.nhlstenden.amazonsimulatie.models;

import java.util.UUID;

public class RackPosition implements CanHoldRacks, HasPosition {

    private final Position position;
    private Rack rack = null;
    private UUID uuid;

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
        rack.setPosition(position.x, position.y, position.z);
    }

}

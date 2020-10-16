package com.nhlstenden.amazonsimulatie.models;

import java.util.UUID;

// size 1 5 1
public class Rack {

    private UUID uuid;
    private final Position position;

    public Rack(double x, double y, double z)
    {
        position = new Position(x, y, z);
        this.uuid = UUID.randomUUID();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(double x, double y, double z)
    {
        this.position.set(x, y, z);
    }

    public String getUUID()
    {
        return this.uuid.toString();
    }
}

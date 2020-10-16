package com.nhlstenden.amazonsimulatie.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class MovableObject implements Updatable {

    protected Position position;
    protected double rotationX, rotationY, rotationZ;
    private UUID uuid;

    public MovableObject(double x, double y, double z) {
        position = new Position(x, y, z);

        this.uuid = UUID.randomUUID();
    }

    public String getUUID() {
        return this.uuid.toString();
    }

    public abstract boolean update();

    public double getX() {
        return this.position.getX();
    }

    public double getY() {
        return this.position.getY();
    }

    public double getZ() {
        return this.position.getZ();
    }

    public double getRotationX() {
        return this.rotationX;
    }

    public double getRotationY() {
        return this.rotationY;
    }

    public double getRotationZ() {
        return this.rotationZ;
    }

}
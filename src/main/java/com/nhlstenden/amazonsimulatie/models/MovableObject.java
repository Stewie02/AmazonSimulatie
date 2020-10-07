package com.nhlstenden.amazonsimulatie.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class MovableObject implements Updatable {

    protected double x, y, z;
    protected double rotationX, rotationY, rotationZ;
    protected double xSpeed, ySpeed, zSpeed;
    private UUID uuid;

    public MovableObject(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.uuid = UUID.randomUUID();
    }

    public String getUUID() {
        return this.uuid.toString();
    }

    public abstract boolean update();

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
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
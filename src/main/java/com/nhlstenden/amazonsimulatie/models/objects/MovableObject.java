package com.nhlstenden.amazonsimulatie.models.objects;

import com.nhlstenden.amazonsimulatie.models.Position;
import com.nhlstenden.amazonsimulatie.models.WorldChanges.WorldChange;
import com.nhlstenden.amazonsimulatie.models.objects.interfaces.Object3D;
import com.nhlstenden.amazonsimulatie.models.objects.interfaces.Updatable;

import java.util.UUID;

public abstract class MovableObject implements Updatable, Object3D {

    protected Position position;
    private double rotationX, rotationY, rotationZ;
    private UUID uuid;

    public MovableObject(double x, double y, double z) {
        position = new Position(x, y, z);

        this.uuid = UUID.randomUUID();
    }

    public MovableObject(Position pos) {
        position = new Position(pos.getX(), pos.getY(), pos.getZ());

        this.uuid = UUID.randomUUID();
    }

    public String getUUID() {
        return this.uuid.toString();
    }

    public abstract WorldChange update();

    public Position getPosition() {
        return this.position;
    }

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

    public abstract String getType();

}
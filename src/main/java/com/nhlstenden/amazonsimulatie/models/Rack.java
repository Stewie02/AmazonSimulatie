package com.nhlstenden.amazonsimulatie.models;

// size 1 5 1
public class Rack extends MovableObject implements Object3D {

    public Rack(double x, double y, double z)
    {
        super(x, y, z);
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public String getType() {
        return Rack.class.getSimpleName().toLowerCase();
    }
}

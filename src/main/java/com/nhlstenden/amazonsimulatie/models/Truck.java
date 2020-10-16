package com.nhlstenden.amazonsimulatie.models;

public class Truck extends MovableObject implements Object3D {

    public Truck(double x, double y, double z)
    {
        super(x, y, z);
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public String getType() {
        return Truck.class.getSimpleName().toLowerCase();
    }
}

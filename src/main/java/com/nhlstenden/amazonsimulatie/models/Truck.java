package com.nhlstenden.amazonsimulatie.models;

public class Truck extends MovableObject implements Object3D {

    private boolean forward = false;

    public Truck(double x, double y, double z)
    {
        super(x, y, z);
    }

    @Override
    public boolean update() {
        if (this.position.getZ() > -7.0 || this.position.getZ() < -19.1)
        {
            forward = !forward;
        }
        if (!forward) this.position.z -= 0.2;
        else this.position.z += 0.2;
        return true;
    }

    @Override
    public String getType() {
        return Truck.class.getSimpleName().toLowerCase();
    }
}

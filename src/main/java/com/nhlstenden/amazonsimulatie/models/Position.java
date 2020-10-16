package com.nhlstenden.amazonsimulatie.models;

public class Position {

    public double x, y, z;

    public Position (double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return roundThreeDecimals(x);
    }

    public double getY() {
        return roundThreeDecimals(y);
    }

    public double getZ() {
        return roundThreeDecimals(z);
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    private double roundThreeDecimals(double value)
    {
        return (double)Math.round(value * 1000d) / 1000d;
    }


}

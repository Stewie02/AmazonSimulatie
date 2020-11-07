package com.nhlstenden.amazonsimulatie.models.position;

public class RealPosition implements Position {

    public double x, y, z;

    /**
     * Sets the x, y and z-coordinate to the x, y and z given as parameter
     * @param x The X-coordinate
     * @param y The Y-coordinate
     * @param z The Z-coordinate
     */
    public RealPosition(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns the X-coordinate of the Position
     * @return The X-coordinate
     */
    @Override
    public double getX() {
        return roundThreeDecimals(x);
    }

    /**
     * Returns the Y-coordinate of the Position
     * @return The Y-coordinate
     */
    @Override
    public double getY() {
        return roundThreeDecimals(y);
    }

    /**
     * Returns the Z-coordinate of the Position
     * @return The Z-coordinate
     */
    @Override
    public double getZ() {
        return roundThreeDecimals(z);
    }

    /**
     * Change all the values of the Position
     * @param x The new X-coordinate
     * @param y The new Y-coordinate
     * @param z The new Z-coordinate
     */
    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Sets the X-coordinate of the Position
     * @param x The new X-coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the Y-coordinate of the Position
     * @param y The new X-coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Sets the Z-coordinate of the Position
     * @param z The new X-coordinate
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Takes in a double and rounds it off to three decimals.
     * @param value The double to round off
     * @return The rounded double
     */
    private double roundThreeDecimals(double value)
    {
        return (double)Math.round(value * 1000d) / 1000d;
    }


}

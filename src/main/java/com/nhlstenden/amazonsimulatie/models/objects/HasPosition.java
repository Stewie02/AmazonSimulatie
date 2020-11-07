package com.nhlstenden.amazonsimulatie.models.objects;

import com.nhlstenden.amazonsimulatie.models.position.Position;
import com.nhlstenden.amazonsimulatie.models.position.ProxyPosition;
import com.nhlstenden.amazonsimulatie.models.position.RealPosition;

/**
 * This abstract class is extended by objects which has Positions
 */
public abstract class HasPosition {

    protected final RealPosition position;

    /**
     * Give the x, y and z coordinates
     * @param x The x of the starting position
     * @param y The y of the starting position
     * @param z The z of the starting position
     */
    public HasPosition(double x, double y, double z) {
        this.position = new RealPosition(x, y, z);
    }

    /**
     * A ProxyPosition of the Position
     * @return An ProxyPosition of the Position
     */
    public Position getPosition() {
        return new ProxyPosition(this.position);
    }

    /**
     * Returns the X coordinate
     * @return The X coordinate
     */
    public double getX() {
        return this.position.getX();
    }

    /**
     * Returns the Y coordinate
     * @return The Y coordinate
     */
    public double getY() {
        return this.position.getY();
    }

    /**
     * Return the Z coordinate
     * @return The Z coordinate
     */
    public double getZ() {
        return this.position.getZ();
    }
}

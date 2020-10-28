package com.nhlstenden.amazonsimulatie.models.position;

import com.nhlstenden.amazonsimulatie.models.position.RealPosition;

public class ProxyPosition implements Position {

    private final RealPosition position;

    /**
     * Give this constructor the Position object to want to make a ProxyPosition of
     * @param position The Position object to create the ProxyPosition of
     */
    public ProxyPosition(RealPosition position) {
        this.position = position;
    }

    /**
     * Returns the X-coordinate of the Position
     * @return the X-coordinate
     */
    @Override
    public double getX() {
        return this.position.getX();
    }

    /**
     * Returns the Y-coordinate of the Position
     * @return the Y-coordinate
     */
    @Override
    public double getY() {
        return this.position.getY();
    }

    /**
     * Returns the Z-coordinate of the Position
     * @return the Z-coordinate
     */
    @Override
    public double getZ() {
        return this.position.getZ();
    }

}

package com.nhlstenden.amazonsimulatie.models.position;

import com.nhlstenden.amazonsimulatie.models.position.RealPosition;

public class ProxyPosition implements Position {

    private final RealPosition position;

    public ProxyPosition(RealPosition position) {
        this.position = position;
    }

    @Override
    public double getX() {
        return this.position.getX();
    }

    @Override
    public double getY() {
        return this.position.getY();
    }

    @Override
    public double getZ() {
        return this.position.getZ();
    }

}

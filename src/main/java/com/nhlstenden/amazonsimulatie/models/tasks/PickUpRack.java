package com.nhlstenden.amazonsimulatie.models.tasks;

import com.nhlstenden.amazonsimulatie.models.Rack;

public class PickUpRack implements Task {

    private final Rack rackToPickUp;

    public PickUpRack(Rack rackToPickUp) {
        this.rackToPickUp = rackToPickUp;
    }

    public Rack getRackToPickUp() {
        return this.rackToPickUp;
    }

    @Override
    public String getType() {
        return PickUpRack.class.getSimpleName();
    }

}

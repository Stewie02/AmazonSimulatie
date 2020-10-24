package com.nhlstenden.amazonsimulatie.models.tasks;

import com.nhlstenden.amazonsimulatie.models.CanHoldRacks;
import com.nhlstenden.amazonsimulatie.models.Rack;

public class DropOffRack implements Task {

    private final Rack rackToDropOff;
    private final CanHoldRacks dropOffPosition;

    public DropOffRack(Rack rackToDropOff, CanHoldRacks dropOffPosition) {
        this.rackToDropOff = rackToDropOff;
        this.dropOffPosition = dropOffPosition;
    }

    public Rack getRackToDropOff() {
        return this.rackToDropOff;
    }

    public CanHoldRacks getDropOffPosition() {
        return this.dropOffPosition;
    }

    @Override
    public String getType() {
        return DropOffRack.class.getSimpleName();
    }
}

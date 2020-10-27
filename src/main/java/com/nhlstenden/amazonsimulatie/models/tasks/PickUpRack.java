package com.nhlstenden.amazonsimulatie.models.tasks;

import com.nhlstenden.amazonsimulatie.models.objects.Rack;

public class PickUpRack implements Task {

    private final Rack rackToPickUp;
    private boolean needToBuildRack;

    public PickUpRack(Rack rackToPickUp) {
        this.rackToPickUp = rackToPickUp;
    }

    public Rack getRackToPickUp() {
        return this.rackToPickUp;
    }

    public void rackNeedsToBuild() {

    }

    @Override
    public String getType() {
        return PickUpRack.class.getSimpleName();
    }

}

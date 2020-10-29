package com.nhlstenden.amazonsimulatie.models.tasks;

import com.nhlstenden.amazonsimulatie.models.objects.Rack;
import com.nhlstenden.amazonsimulatie.models.objects.interfaces.CanHoldRacks;

/**
 * The DropOffRack contains all the information the Robot needs to drop off a Rack
 */
public class DropOffRack implements Task {

    private final CanHoldRacks dropOffPosition;
    private final Rack rackToDropOff;

    /**
     * Takes in the Rack that needs to be dropped off
     * And the CanHoldRacks object that will hold the Rack
     * @param dropOffPosition The Position to drop it to
     * @param rackToDropOff The Rack that will be dropped off
     */
    public DropOffRack(CanHoldRacks dropOffPosition, Rack rackToDropOff) {
        this.dropOffPosition = dropOffPosition;
        this.rackToDropOff = rackToDropOff;
    }

    /**
     * Returns the drop off Position
     * @return The drop off Position
     */
    public CanHoldRacks getDropOffPosition() {
        return this.dropOffPosition;
    }

    public Rack getRackToDropOff() {
        return this.rackToDropOff;
    }
}

package com.nhlstenden.amazonsimulatie.models.tasks;

import com.nhlstenden.amazonsimulatie.models.objects.Rack;

/**
 * The PickUpRack class is a Task which holds the information needed for the Robot to pick up a Rack
 */
public class PickUpRack implements Task {

    private final Rack rackToPickUp;

    /**
     * Get is the Rack that needs to be picked up and creates the Task
     * @param rackToPickUp Rack that needs to be picked up
     */
    public PickUpRack(Rack rackToPickUp) {
        this.rackToPickUp = rackToPickUp;
    }

    /**
     * Returns the Rack that needs to be picked up
     * @return Rack that needs to be picked up
     */
    public Rack getRackToPickUp() {
        return this.rackToPickUp;
    }


}

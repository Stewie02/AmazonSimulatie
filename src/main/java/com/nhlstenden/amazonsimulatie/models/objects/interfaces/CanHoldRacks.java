package com.nhlstenden.amazonsimulatie.models.objects.interfaces;

import com.nhlstenden.amazonsimulatie.models.objects.Rack;

public interface CanHoldRacks {

    /**
     * Sets the Rack that is holding
     * @param rack The Rack that needs to be set
     */
    void setRack(Rack rack);

    /**
     * Return the Rack that it's holding
     * @return The Rack that the object is holding
     */
    Rack getRack();

    /**
     * Returns the UUID of the object
     * @return The UUID
     */
    String getUUID();

}
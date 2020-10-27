package com.nhlstenden.amazonsimulatie.models.objects;

import com.nhlstenden.amazonsimulatie.models.objects.interfaces.CanHoldRacks;

import java.util.UUID;

/**
 * A rack is the object being moved in the warehouse.
 * It has a holder, which has to implement interface CanHoldRacks
 * On the client side the Position of the Rack is always equal to the Position of the Holder
 * That's why the Rack doesn't have a Position
 */
public class Rack {

    private final UUID uuid;
    private CanHoldRacks holder;
    private boolean isAvailable = true;

    /**
     * Will create the object with a random UUID
     */
    public Rack(CanHoldRacks holder)
    {
        this.holder = holder;
        this.uuid = UUID.randomUUID();
    }

    /**
     * Returns the UUID of the Rack
     * @return The UUID
     */
    public String getUUID()
    {
        return this.uuid.toString();
    }

    /**
     * Returns the holder of the Rack
     * @return The holder
     */
    public CanHoldRacks getHolder() {
        return holder;
    }

    /**
     * Sets the holder of the Rack
     * @param holder The new Holder
     */
    public void setHolder(CanHoldRacks holder)
    {
        this.holder = holder;
    }

    /**
     * Returns if the Rack is available, if it isn't another Rack has of will take the Rack
     * @return Boolean if the Rack is available
     */
    public boolean isAvailable() {
        return this.isAvailable;
    }

    /**
     * Sets the availability of the Rack, true if it is available
     * @param availability true if the Rack needs to be available, false if not
     */
    public void setAvailability(boolean availability) {
        this.isAvailable = availability;
    }

}

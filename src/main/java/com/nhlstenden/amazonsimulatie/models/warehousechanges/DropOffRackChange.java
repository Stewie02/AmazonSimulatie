package com.nhlstenden.amazonsimulatie.models.warehousechanges;

import com.nhlstenden.amazonsimulatie.jsonBuilders.JSONBuilder;
import com.nhlstenden.amazonsimulatie.models.objects.interfaces.CanHoldRacks;
import com.nhlstenden.amazonsimulatie.models.objects.Robot;

/**
 * The DropOffRackChange holds all the information the client needs to know about a Rack that is dropped off
 */
public class DropOffRackChange implements WarehouseChange {

    private final Robot dropper;
    private final CanHoldRacks newHolder;

    /**
     * This constructor takes in different information about the change and stores them
     * @param dropper The Robot that will drop the Rack
     * @param newHolder The new holder of the Rack
     */
    public DropOffRackChange(Robot dropper, CanHoldRacks newHolder) {
        this.dropper = dropper;
        this.newHolder = newHolder;
    }

    /**
     * This function will return the JSON string that the client needs to update the world
     * @return String in JSON format
     */
    @Override
    public String getParametersString() {
        return new JSONBuilder()
                .put("robot", dropper.getUUID())
                .put("position", newHolder.getUUID())
                .toString();
    }

    public String getCommand() {
        return "drop_off";
    }
}
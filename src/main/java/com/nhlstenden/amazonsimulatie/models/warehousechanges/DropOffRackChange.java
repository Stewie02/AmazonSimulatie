package com.nhlstenden.amazonsimulatie.models.warehousechanges;

import com.nhlstenden.amazonsimulatie.models.objects.interfaces.CanHoldRacks;
import com.nhlstenden.amazonsimulatie.models.objects.Rack;
import com.nhlstenden.amazonsimulatie.models.objects.Robot;

import static com.nhlstenden.amazonsimulatie.helpers.JSONHelper.createKeyValueJSON;

/**
 * The DropOffRackChange holds all the information the client needs to know about a Rack that is dropped off
 */
public class DropOffRackChange implements WarehouseChange {

    private final Robot dropper;
    private final CanHoldRacks newHolder;
    private final Rack rack;

    /**
     * This constructor takes in different information about the change and stores them
     * @param dropper The Robot that dropped the Rack
     * @param newHolder The new holder of the Rack
     * @param rack The rack that is dropped
     */
    public DropOffRackChange(Robot dropper, CanHoldRacks newHolder, Rack rack) {
        this.dropper = dropper;
        this.newHolder = newHolder;
        this.rack = rack;
    }

    /**
     * This function will return the JSON string that the client needs to update the world
     * @return String in JSON format
     */
    @Override
    public String getParametersString() {
        return "{" +
                  createKeyValueJSON("robot", dropper.getUUID()) + "," +
                  createKeyValueJSON("position", newHolder.getUUID()) +
                "}";
    }

    public String getCommand() {
        return "drop_off";
    }
}
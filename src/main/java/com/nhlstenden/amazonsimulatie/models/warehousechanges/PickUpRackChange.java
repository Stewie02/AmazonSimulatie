package com.nhlstenden.amazonsimulatie.models.warehousechanges;

import com.nhlstenden.amazonsimulatie.jsonBuilders.JSONBuilder;
import com.nhlstenden.amazonsimulatie.models.objects.Rack;
import com.nhlstenden.amazonsimulatie.models.objects.Robot;

/**
 * The PickUpRackChange class contains all the information the client needs about a Rack that is picked up
 */
public class PickUpRackChange implements WarehouseChange {

    private final Robot robot;
    private final Rack rack;

    /**
     * Takes in the different information needed at the client side
     * @param robot Robot that picked up the Rack
     * @param rack Rack that is picked up by the Robot
     */
    public PickUpRackChange(Robot robot, Rack rack) {
        this.robot = robot;
        this.rack = rack;
    }

    /**
     * Returns the JSON object which is sent to the client
     * @return String in JSON format
     */
    @Override
    public String getParametersString() {
        return new JSONBuilder()
                .put("robot", robot.getUUID())
                .put("rack", rack.getUUID())
                .toString();
    }

    /**
     * Returns the command that the client reads
     * @return the command
     */
    public String getCommand() {
        return "pick_up";
    }

}

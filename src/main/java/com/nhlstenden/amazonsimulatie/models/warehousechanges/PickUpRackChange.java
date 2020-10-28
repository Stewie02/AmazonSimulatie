package com.nhlstenden.amazonsimulatie.models.warehousechanges;

import com.nhlstenden.amazonsimulatie.models.objects.Rack;
import com.nhlstenden.amazonsimulatie.models.objects.Robot;

import static com.nhlstenden.amazonsimulatie.helpers.JSONHelper.createKeyValueJSON;

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
        StringBuilder string = new StringBuilder("{");
        string.append(createKeyValueJSON("robot", robot.getUUID())).append(",");
        string.append(createKeyValueJSON("rack", rack.getUUID()));
        string.append("}");
        return string.toString();
    }

    /**
     * Returns the command that the client reads
     * @return the command
     */
    public String getCommand() {
        return "pick_up";
    }

}

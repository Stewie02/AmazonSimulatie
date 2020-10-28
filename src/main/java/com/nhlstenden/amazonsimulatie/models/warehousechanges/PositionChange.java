package com.nhlstenden.amazonsimulatie.models.warehousechanges;

import com.nhlstenden.amazonsimulatie.models.objects.MovableObject;
import com.nhlstenden.amazonsimulatie.models.objects.Robot;

import static com.nhlstenden.amazonsimulatie.helpers.JSONHelper.surroundString;

/**
 * The PositionChange class contains all the information the client needs about an object that changed it's Position
 */
public class PositionChange implements WarehouseChange {
    
    private final MovableObject movedObject;

    /**
     * Takes in the object that is moved
     * @param movedObject Object that moved
     */
    public PositionChange(MovableObject movedObject) {
        this.movedObject = movedObject;
    }

    /**
     * Returns the JSON object which is sent to the client
     * @return String in JSON format
     */
    @Override
    public String getParametersString() {
        String str =  "{"
                + surroundString("uuid") + ":" + surroundString(movedObject.getUUID()) + ","
                + surroundString("type") + ":" + surroundString(movedObject.getType()) + ",";
        if (movedObject instanceof Robot)
            if (((Robot) movedObject).getRack() != null)
                str += surroundString("rack") + ":" + surroundString(((Robot)movedObject).getRack().getUUID()) + ",";

        str +=
                surroundString("x") + ":" + movedObject.getX() + ","
                        + surroundString("y") + ":" + movedObject.getY() + ","
                        + surroundString("z") + ":" + movedObject.getZ() + ","
                        + surroundString("rotationX") + ":" + movedObject.getRotationX() + ","
                        + surroundString("rotationY") + ":" + movedObject.getRotationY() + ","
                        + surroundString("rotationZ") + ":" + movedObject.getRotationZ()
                        + "}";
        return str;
    }

    /**
     * Returns the command for the client
     * @return The command
     */
    public String getCommand() {
        return "update";
    }
}

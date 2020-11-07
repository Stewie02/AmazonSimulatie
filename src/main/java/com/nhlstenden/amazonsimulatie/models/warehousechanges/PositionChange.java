package com.nhlstenden.amazonsimulatie.models.warehousechanges;

import com.nhlstenden.amazonsimulatie.jsonBuilders.JSONBuilder;
import com.nhlstenden.amazonsimulatie.models.objects.MovableObject;
import com.nhlstenden.amazonsimulatie.models.objects.Robot;

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
        JSONBuilder jsonBuilder = new JSONBuilder();
        jsonBuilder
                .put("uuid", movedObject.getUUID())
                .put("type", movedObject.getType())
                .put("x", movedObject.getX())
                .put("y", movedObject.getY())
                .put("z", movedObject.getZ())
                .put("rotationX", movedObject.getRotationX())
                .put("rotationY", movedObject.getRotationY())
                .put("rotationZ", movedObject.getRotationZ());
        if (movedObject instanceof Robot)
            if (((Robot) movedObject).getRack() != null)
                jsonBuilder.put("rack", ((Robot)movedObject).getRack().getUUID());
        return jsonBuilder.toString();
    }

    /**
     * Returns the command for the client
     * @return The command
     */
    public String getCommand() {
        return "update";
    }
}

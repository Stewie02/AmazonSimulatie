package com.nhlstenden.amazonsimulatie.models.WorldChanges;

import com.nhlstenden.amazonsimulatie.models.objects.MovableObject;
import com.nhlstenden.amazonsimulatie.models.objects.Robot;

public class PositionChange implements WorldChange {
    
    private final MovableObject movedObject;
    private final String command = "update";

    public PositionChange(MovableObject movedObject) {
        this.movedObject = movedObject;
    }
    
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

    public String getCommand() {
        return this.command;
    }

    private String createKeyValueJSON(String key, String value) {
        return surroundString(key) + ": " + surroundString(value);
    }

    private String surroundString(String s) {
        return "\"" + s + "\"";
    }
}

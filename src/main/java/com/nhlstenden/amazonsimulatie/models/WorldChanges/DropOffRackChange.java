package com.nhlstenden.amazonsimulatie.models.WorldChanges;

import com.nhlstenden.amazonsimulatie.models.CanHoldRacks;
import com.nhlstenden.amazonsimulatie.models.Rack;
import com.nhlstenden.amazonsimulatie.models.Robot;

public class DropOffRackChange implements WorldChange {

    private final Robot dropper;
    private final CanHoldRacks newHolder;
    private final Rack rack;
    private final String command = "drop_off";

    public DropOffRackChange(Robot dropper, CanHoldRacks newHolder, Rack rack) {
        this.dropper = dropper;
        this.newHolder = newHolder;
        this.rack = rack;
    }

    @Override
    public String getParametersString() {
        StringBuilder string = new StringBuilder("{");
        string.append(createKeyValueJSON("robot", dropper.getUUID())).append(",");
        string.append(createKeyValueJSON("position", newHolder.getUUID()));
        string.append("}");
        return string.toString();
    }

    public Rack getRack() {
        return this.rack;
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


/*
{
        command: drop_off
        parameters: {
            robot: robot_uuid
            position: position_uuid
        }
    }
 */
package com.nhlstenden.amazonsimulatie.models.worldchanges;

import com.nhlstenden.amazonsimulatie.models.objects.Rack;
import com.nhlstenden.amazonsimulatie.models.objects.Robot;

public class PickUpRackChange implements WorldChange {

    private final Robot robot;
    private final Rack rack;
    private final String command = "pick_up";


    public PickUpRackChange(Robot robot, Rack rack) {
        this.robot = robot;
        this.rack = rack;
    }

    @Override
    public String getParametersString() {

        StringBuilder string = new StringBuilder("{");
        string.append(createKeyValueJSON("robot", robot.getUUID())).append(",");
        string.append(createKeyValueJSON("rack", rack.getUUID()));
        string.append("}");
        return string.toString();
    }

    public String getCommand() {
        return this.command;
    }

    public Rack getRack() {
        return this.rack;
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
        command: pick_up
        parameters: {
            robot: robot_uuid
            rack: rack_uuid
        }
    }
 */
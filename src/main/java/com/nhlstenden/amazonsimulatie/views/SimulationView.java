package com.nhlstenden.amazonsimulatie.views;

import java.io.IOException;
import java.util.List;

import com.nhlstenden.amazonsimulatie.base.Command;
import com.nhlstenden.amazonsimulatie.jsonBuilders.JSONArrayBuilder;
import com.nhlstenden.amazonsimulatie.jsonBuilders.JSONBuilder;
import com.nhlstenden.amazonsimulatie.models.objects.interfaces.Object3D;

import com.nhlstenden.amazonsimulatie.models.objects.Robot;
import com.nhlstenden.amazonsimulatie.models.warehousechanges.WarehouseChange;
import com.nhlstenden.amazonsimulatie.models.objects.RackPosition;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * This is the view that sends the information regarding the simulation to the client
 */
public class SimulationView {
    private final WebSocketSession session;
    private Command onClose;

    /**
     * Adds the session to this specific view
     * @param session The WebSocketSession with the browser
     */
    public SimulationView(WebSocketSession session) {
        this.session = session;
    }

    /**
     * This function is used when the View is added
     * It sends the data about the Object3D to the browser with the build command
     * @param data The object to send to the client
     */
    public void build(Object3D data) {
        sendMessage(
            new JSONBuilder()
                .put("command", "build")
                .put("parameters", jsonifyObject3D(data)).toString()
        );
    }

    /**
     * Sends a List of RackPositions to the client
     * @param rackPositions RackPosition to send
     */
    public void sendRackPositions(List<RackPosition> rackPositions)
    {
        // Create the JSON array with all the RackPositions
        JSONArrayBuilder jsonArrayRackPositions = new JSONArrayBuilder();
        for (RackPosition rackPosition : rackPositions) {
            JSONBuilder jsonBuilder = new JSONBuilder();
            jsonBuilder
                    .put("uuid", rackPosition.getUUID())
                    .put("x", rackPosition.getX())
                    .put("y", rackPosition.getY())
                    .put("z", rackPosition.getZ());
            if (rackPosition.getRack() != null) jsonBuilder.put("rack", rackPosition.getRack().getUUID());
            jsonArrayRackPositions.put(jsonBuilder);
        }

        // Put everything in the JSONBuilder and send it
        JSONBuilder jsonBuilder = new JSONBuilder()
            .put("command", "rack_positions")
            .put("parameters", jsonArrayRackPositions);
        sendMessage(jsonBuilder.toString());
    }

    /**
     * Sends a WarehouseChange to the client
     * @param warehouseChange WarehouseChange to send
     */
    public void sendWorldChange(WarehouseChange warehouseChange) {
        sendMessage(
                new JSONBuilder()
                    .put("command", warehouseChange.getCommand())
                    .putNoSurrounding("parameters", warehouseChange.getParametersString())
                        .toString()
        );
    }

    /**
     * Set the command to execute when the WebSocketSession is closed
     * @param command Command to execute
     */
    public void onViewClose(Command command) {
        onClose = command;
    }

    public void sendMessage(String message) {
        try {
            if(this.session.isOpen()) {
                this.session.sendMessage(new TextMessage(message));
            }
            else {
                this.onClose.execute();
            }

        } catch (IOException e) {
            this.onClose.execute();
        }
    }

    /**
     * Takes in an Object3D and return the JSONBuilder with all the data
     * @param object Object3D to create the JSONBuilder of
     * @return The JSONBuilder with the Object3D data
     */
    private JSONBuilder jsonifyObject3D(Object3D object) {
        JSONBuilder json = new JSONBuilder()
                .put("uuid", object.getUUID())
                .put("type", object.getType())
                .put("x", object.getX())
                .put("y", object.getY())
                .put("z", object.getZ())
                .put("rotationX", object.getRotationX())
                .put("rotationY", object.getRotationY())
                .put("rotationZ", object.getRotationZ());

        if (object instanceof Robot) {
            // If the rack != null the value will contain the UUID else it will be undefined
            String rack = ((Robot) object).getRack() != null ? ((Robot) object).getRack().getUUID() : "undefined";
            json.put("rack", rack);
        }
        return json;
    }
}
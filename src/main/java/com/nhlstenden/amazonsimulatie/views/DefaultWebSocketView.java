package com.nhlstenden.amazonsimulatie.views;

import java.io.IOException;
import java.util.List;

import com.nhlstenden.amazonsimulatie.base.Command;
import com.nhlstenden.amazonsimulatie.models.objects.interfaces.Object3D;

import com.nhlstenden.amazonsimulatie.models.objects.Robot;
import com.nhlstenden.amazonsimulatie.models.WorldChanges.PickUpRackChange;
import com.nhlstenden.amazonsimulatie.models.WorldChanges.WorldChange;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Node;
import com.nhlstenden.amazonsimulatie.models.objects.RackPosition;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/*
 * Deze class is de standaard websocketview. De class is een andere variant
 * van een gewone view. Een "normale" view is meestal een schermpje op de PC,
 * maar in dit geval is het wat de gebruiker ziet in de browser. Het behandelen
 * van een webpagina als view zie je vaker wanneer je te maken hebt met
 * serversystemen. In deze class wordt de WebSocketSession van de client opgeslagen,
 * waarmee de view class kan communiceren met de browser.
 */
public class DefaultWebSocketView implements View {
    private WebSocketSession session;
    private Command onClose;

    public DefaultWebSocketView(WebSocketSession session) {
        this.session = session;
    }

    /*
     * Deze methode wordt aangroepen vanuit de controller wanneer er een update voor
     * de views is. Op elke view wordt dan de update methode aangroepen, welke een
     * JSON pakketje maakt van de informatie die verstuurd moet worden. Deze JSON
     * wordt naar de browser verstuurd, welke de informatie weer afhandeld.
     */
    @Override
    public void update(String event, Object3D data) {
        sendMessage("{"
                + surroundString("command") + ": " + surroundString(event) + ","
                + surroundString("parameters") + ": " + jsonifyObject3D(data)
                + "}");
    }

    public void sendRackPositions(List<RackPosition> rackPositions)
    {
        int amountOfPositions = rackPositions.size();

        StringBuilder stringBuilder = new StringBuilder("{"
                + surroundString("command") + ": " + surroundString("rack_positions") + ","
                + surroundString("parameters") + ": " + "[");
        for (int i = 0; i < amountOfPositions; i++)
        {
            RackPosition rackPosition = rackPositions.get(i);

            stringBuilder.append("{");
            stringBuilder.append(surroundString("uuid")).append(": ").append(surroundString(rackPosition.getUUID())).append(",");
            stringBuilder.append(surroundString("x")).append(": ").append(surroundString(Double.toString(rackPosition.getPosition().getX()))).append(",");
            stringBuilder.append(surroundString("y")).append(": ").append(surroundString(Double.toString(rackPosition.getPosition().getY()))).append(",");
            stringBuilder.append(surroundString("z")).append(": ").append(surroundString(Double.toString(rackPosition.getPosition().getZ())));
            if (rackPosition.getRack() != null) stringBuilder.append(",").append(surroundString("rack")).append(": ").append(surroundString(rackPosition.getRack().getUUID()));
            stringBuilder.append("}");
            if (i + 1 == amountOfPositions) stringBuilder.append("]");
            else stringBuilder.append(",");
        }
        stringBuilder.append("}");
        sendMessage(stringBuilder.toString());
    }

    @Override
    public void sendWorldChange(WorldChange worldChange) {

        if (worldChange instanceof PickUpRackChange) System.out.println("Picking up");

        sendMessage(
                "{"
                        + surroundString("command") + ": " + surroundString(worldChange.getCommand()) + ","
                        + surroundString("parameters") + ": " + worldChange.getParametersString()
                + "}"
        );
    }

    @Override
    public void sendNode(String event, Node node)
    {
        sendMessage("{"
                + surroundString("command") + ": " + surroundString(event) + ","
                + surroundString("parameters") + ": " + "{"
                + surroundString("x") + ":" + node.getPosition().getX() + ","
                + surroundString("y") + ":" + node.getPosition().getY() + ","
                + surroundString("z") + ":" + node.getPosition().getZ()
                + "}"
                + "}");
    }

    @Override
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

    /*
     * Deze methode maakt van een Object3D object een JSON pakketje om verstuurd te worden
     * naar de client.
     */
    private String jsonifyObject3D(Object3D object) {
        String str =   "{"
                + surroundString("uuid") + ":" + surroundString(object.getUUID()) + ","
                + surroundString("type") + ":" + surroundString(object.getType()) + ",";

        if (object instanceof Robot)
            if (((Robot) object).getRack() != null)
                str += surroundString("rack") + ":" + surroundString(((Robot)object).getRack().getUUID()) + ",";
            else str += surroundString("rack") + ":" + surroundString("undefined") + ",";

        str += surroundString("x") + ":" + object.getX() + ","
                + surroundString("y") + ":" + object.getY() + ","
                + surroundString("z") + ":" + object.getZ() + ","
                + surroundString("rotationX") + ":" + object.getRotationX() + ","
                + surroundString("rotationY") + ":" + object.getRotationY() + ","
                + surroundString("rotationZ") + ":" + object.getRotationZ()
              + "}";

        return str;
    }

    private String surroundString(String s) {
        return "\"" + s + "\"";
    }
}
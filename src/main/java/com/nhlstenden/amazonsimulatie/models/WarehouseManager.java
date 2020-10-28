package com.nhlstenden.amazonsimulatie.models;

import com.nhlstenden.amazonsimulatie.models.creators.NodeListCreator;
import com.nhlstenden.amazonsimulatie.models.creators.TaskCreator;
import com.nhlstenden.amazonsimulatie.models.creators.WorldObjectsCreator;
import com.nhlstenden.amazonsimulatie.models.objects.*;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Dijkstra;
import com.nhlstenden.amazonsimulatie.models.position.Position;
import com.nhlstenden.amazonsimulatie.models.tasks.Assignment;
import com.nhlstenden.amazonsimulatie.models.tasks.GoToPosition;
import com.nhlstenden.amazonsimulatie.models.tasks.Task;
import com.nhlstenden.amazonsimulatie.models.warehousechanges.WarehouseChange;
import com.nhlstenden.amazonsimulatie.models.objects.interfaces.Object3D;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Node;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
 * Deze class is een versie van het model van de simulatie. In dit geval is het
 * de 3D wereld die we willen modelleren (magazijn). De zogenaamde domain-logic,
 * de logica die van toepassing is op het domein dat de applicatie modelleerd, staat
 * in het model. Dit betekent dus de logica die het magazijn simuleert.
 */
public class WarehouseManager {
    /*
     * Dit onderdeel is nodig om veranderingen in het model te kunnen doorgeven aan de controller.
     * Het systeem werkt al as-is, dus dit hoeft niet aangepast te worden.
     */
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private final RackPosition[][] rackPositions;
    private final Robot[] robots;
    private final Truck truck;
    private final Dijkstra dijkstra;

    /*
     * De wereld maakt een lege lijst voor worldObjects aan. Daarin wordt nu één robot gestopt.
     * Deze methode moet uitgebreid worden zodat alle objecten van de 3D wereld hier worden gemaakt.
     */
    public WarehouseManager() {
        rackPositions = new RackPosition[Constants.AMOUNT_OF_RACKS_HEIGHT][Constants.AMOUNT_OF_RACKS_WIDTH];
        WorldObjectsCreator.createRackPositions(rackPositions);

        dijkstra = new Dijkstra(NodeListCreator.createNodeList(rackPositions));

        robots = new Robot[Constants.AMOUNT_OF_ROBOTS];
        WorldObjectsCreator.createRobots(robots, dijkstra.getNodes());

        truck = new Truck(dijkstra.getNodes().get(2));
    }


    public void update() {
        for (MovableObject object : robots)
        {
            WarehouseChange warehouseChange = object.update();
            if (warehouseChange != null)  pcs.firePropertyChange("", null, warehouseChange);
            if (object instanceof Robot)
                if (((Robot) object).finishedAllAssignments()) {
                    // TODO: Add tasks to the robots
                    truck.redeemAssignment(((Robot) object).getLastAssignment());
                    if (truck.assignmentsAvailable()) {
                        Assignment a = truck.getNextAssignment();
                        if (a != null) {
                            Position previousPosition = ((Robot) object).getLatestNodePosition();
                            for (Task task : a.getTasks())
                                if (task instanceof GoToPosition) {
                                    ((GoToPosition) task).setRoute(dijkstra.giveShortestPath(previousPosition, ((GoToPosition) task).getFinalNode()));
                                    previousPosition = ((GoToPosition) task).getFinalNode().getPosition();
                                }
                            ((Robot) object).addAssignment(a);
                        }
                    }
                }
        }

        WarehouseChange truckChange = truck.update();
        if (truckChange != null) pcs.firePropertyChange("", null, truckChange);

        if (truck.timeToAddAssignments()) {
            System.out.println("Adding assignments");
            truck.addAssignments(generateNewAssignments());
        }

    }


    /*
     * Standaardfunctionaliteit. Hoeft niet gewijzigd te worden.
     */
    public void addObserver(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    private List<Assignment> generateNewAssignments() {

        List<Assignment> assignments = new ArrayList<>();

        for (int i = 0; i < Constants.AMOUNT_OF_ROBOTS * 4; i++)
        {
            if (new Random().nextInt(100) < 50) {
                assignments.add(TaskCreator.createBringToTruckAssignment(rackPositions, truck));
            }
            else {
                assignments.add(TaskCreator.createBringToRackPositionAssignment(rackPositions, truck));
            }
        }
        return assignments;
    }

    public List<Node> getNodes()
    {
        return this.dijkstra.getNodes();
    }

    public List<Object3D> getAllMovableObjects() {
        List<Object3D> allObjects = new ArrayList<>();

        for (MovableObject robot : robots)
            allObjects.add(new ProxyObject3D(robot));

        allObjects.add(new ProxyObject3D(truck));
        return allObjects;
    }

    public List<RackPosition> getRackPositions() {
        List<RackPosition> rackPositionsList = new ArrayList<>();
        for (RackPosition[] innerRackPositions : rackPositions)
            rackPositionsList.addAll(Arrays.asList(innerRackPositions));
        return rackPositionsList;
    }
}
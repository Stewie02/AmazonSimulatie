package com.nhlstenden.amazonsimulatie.models;

import com.nhlstenden.amazonsimulatie.models.position.Position;
import com.nhlstenden.amazonsimulatie.models.warehousechanges.WarehouseChange;
import com.nhlstenden.amazonsimulatie.models.creators.NodeListCreator;
import com.nhlstenden.amazonsimulatie.models.creators.WorldObjectsCreator;
import com.nhlstenden.amazonsimulatie.models.creators.TaskCreator;
import com.nhlstenden.amazonsimulatie.models.objects.*;
import com.nhlstenden.amazonsimulatie.models.objects.interfaces.Object3D;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Dijkstra;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Node;
import com.nhlstenden.amazonsimulatie.models.tasks.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MovableObjectsManager {

    private final RackPosition[][] rackPositions;
    private final Robot[] robots;
    private final Truck truck;
    private final Dijkstra dijkstra;

    public MovableObjectsManager()
    {
        rackPositions = new RackPosition[Constants.AMOUNT_OF_RACKS_HEIGHT][Constants.AMOUNT_OF_RACKS_WIDTH];
        WorldObjectsCreator.createRackPositions(rackPositions);

        dijkstra = new Dijkstra(NodeListCreator.createNodeList(rackPositions));

        robots = new Robot[Constants.AMOUNT_OF_ROBOTS];
        WorldObjectsCreator.createRobots(robots, dijkstra.getNodes());

        truck = new Truck(dijkstra.getNodes().get(2));
    }

    public List<Object3D> getMovableObjectsAsList()
    {
        List<Object3D> allObjects = new ArrayList<>();

        for (MovableObject robot : robots)
            allObjects.add(new ProxyObject3D((Object3D) robot));

        allObjects.add(new ProxyObject3D(truck));
        return allObjects;
    }

    public List<RackPosition> getRackPositions()
    {
        List<RackPosition> rackPositionsList = new ArrayList<>();
        for (RackPosition[] innerRackPositions : rackPositions)
            rackPositionsList.addAll(Arrays.asList(innerRackPositions));
        return rackPositionsList;
    }

    public List<WarehouseChange> update()
    {
        List<WarehouseChange> warehouseChanges = new ArrayList<>();
        for (MovableObject object : robots)
        {
            WarehouseChange warehouseChange = object.update();
            if (warehouseChange != null) warehouseChanges.add(warehouseChange);
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
        if (truckChange != null) warehouseChanges.add(truckChange);

        if (truck.timeToAddAssignments()) {
            System.out.println("Adding assignments");
            truck.addAssignments(generateNewAssignments());
        }

        return warehouseChanges;
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

}

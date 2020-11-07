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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * This class manages the whole warehouse.
 * It contains all the element needed in the warehouse and controls them
 */
public class WarehouseManager {

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private final RackPosition[][] rackPositions;
    private final Robot[] robots;
    private final Truck truck;
    private final Dijkstra dijkstra;

    /*
     * This constructor creates some values that the WareHouseManager needs to control the warehouse
     */
    public WarehouseManager() {
        // First all the RackPositions will be created
        rackPositions = new RackPosition[Constants.AMOUNT_OF_RACKS_HEIGHT][Constants.AMOUNT_OF_RACKS_WIDTH];
        WorldObjectsCreator.createRackPositions(rackPositions);

        // Then the Dijkstra object will be created and initialized
        dijkstra = new Dijkstra(NodeListCreator.createNodeList(rackPositions));

        // All the Robots will be created
        robots = new Robot[Constants.AMOUNT_OF_ROBOTS];
        WorldObjectsCreator.createRobots(robots, dijkstra.getNodes());

        // The Truck is created
        truck = new Truck(dijkstra.getNodes().get(2));
    }

    /**
     * This method will call the update method on all the movable objects
     * All the changes will be sent to the Controller with the PropertyChangeSupport
     */
    public void update() {
        // Iterate through all the robots
        for (Robot robot : robots) {
            // Update the robot and if there is a change, send it to the Controller
            WarehouseChange warehouseChange = robot.update();
            if (warehouseChange != null) pcs.firePropertyChange("", null, warehouseChange);

            // If the robot has finished all it's Assignments, check if the Truck has Assignments
            if (robot.finishedAllAssignments()) {
                truck.redeemAssignment(robot.getLastAssignment());
                if (truck.assignmentsAvailable()) {
                    Assignment a = truck.getNextAssignment();
                    Position previousPosition = robot.getLatestNodePosition();

                    /* Iterate through the Tasks of the Assignment and if the Task is a GoToPosition
                       the route from the Robot to the finalNode will be added
                     */
                    for (Task task : a.getTasks())
                        if (task instanceof GoToPosition) {
                            ((GoToPosition) task).setRoute(dijkstra.giveShortestPath(previousPosition, ((GoToPosition) task).getFinalNode()));
                            previousPosition = ((GoToPosition) task).getFinalNode().getPosition();
                        }
                    robot.addAssignment(a);
                }
            }
        }

        // Update the Truck and send the WorldChange to the Controller if it isn't null
        WarehouseChange truckChange = truck.update();
        if (truckChange != null) pcs.firePropertyChange("", null, truckChange);

        // If it's time for the Truck to add Assignments, add new Assignments
        if (truck.timeToAddAssignments()) truck.addAssignments(generateNewAssignments());

    }

    public void addObserver(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    /**
     * This function generates new Assignments and returns them
     * @return List of Assignments
     */
    private List<Assignment> generateNewAssignments() {

        List<Assignment> assignments = new ArrayList<>();

        // Generate for every robot 4 Assignments and randomize which Assignment it is
        for (int i = 0; i < Constants.AMOUNT_OF_ROBOTS * 4; i++) {
            if (new Random().nextInt(100) < 50) {
                assignments.add(TaskCreator.createBringToTruckAssignment(rackPositions, truck));
            } else {
                assignments.add(TaskCreator.createBringToRackPositionAssignment(rackPositions, truck));
            }
        }
        return assignments;
    }

    /**
     * Returns all the MovableObjects in a List
     * @return List of the MovableObjects in the warehouse
     */
    public List<Object3D> getAllMovableObjects() {
        List<Object3D> allObjects = new ArrayList<>();

        for (MovableObject robot : robots)
            allObjects.add(new ProxyObject3D(robot));

        allObjects.add(new ProxyObject3D(truck));
        return allObjects;
    }

    /**
     * Returns all the RackPositions in the warehouse
     * @return List of RackPositions that are in the warehouse
     */
    public List<RackPosition> getRackPositions() {
        List<RackPosition> rackPositionsList = new ArrayList<>();
        for (RackPosition[] innerRackPositions : rackPositions)
            rackPositionsList.addAll(Arrays.asList(innerRackPositions));
        return rackPositionsList;
    }
}
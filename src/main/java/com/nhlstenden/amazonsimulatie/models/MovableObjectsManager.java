package com.nhlstenden.amazonsimulatie.models;

import com.nhlstenden.amazonsimulatie.models.WorldChanges.WorldChange;
import com.nhlstenden.amazonsimulatie.models.creators.NodeListCreator;
import com.nhlstenden.amazonsimulatie.models.creators.TaskCreator;
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
        fillRackPositionArray();

        dijkstra = new Dijkstra(NodeListCreator.createNodeList(rackPositions));

        robots = new Robot[Constants.AMOUNT_OF_ROBOTS];
        fillRobotArray();

        truck = new Truck(rackPositions, dijkstra.getNodes().get(2));

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

    public List<WorldChange> update()
    {
        List<WorldChange> worldChanges = new ArrayList<>();
        for (MovableObject object : robots)
        {
            WorldChange worldChange = object.update();
            if (worldChange != null) worldChanges.add(worldChange);
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
        // TODO: Do everything with the robot
        WorldChange truckChange = truck.update();
        if (truckChange != null) worldChanges.add(truckChange);

        if (truck.timeToAddAssignments()) {
            System.out.println("Adding assignments");
            truck.addAssignments(generateNewAssignments());
        }

        return worldChanges;
    }

    private List<Assignment> generateNewAssignments() {

        List<Assignment> assignments = new ArrayList<>();

        for (int i = 0; i < Constants.AMOUNT_OF_ROBOTS * 2; i++)
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



    private void fillRobotArray()
    {
        for (int i = 0; i < robots.length; i++) {
            robots[i] = new Robot(dijkstra.getNodes().get(i));
        }
    }

    private void fillRackPositionArray()
    {
        double x, z = 0;
        for (int column = 0; column < rackPositions.length; column++)
        {
            x = 0;
            if (column % 2 == 0 && column != 0) z += Constants.DISTANCE_BETWEEN_RACK_WITH_PATH;
            else if(column != 0) z += Constants.DISTANCE_BETWEEN_RACKS;

            for (int row = 0; row < rackPositions[column].length; row++)
            {
                if (row % 2 == 0 && row != 0) x += Constants.DISTANCE_BETWEEN_RACK_WITH_PATH;
                else if(row != 0) x += Constants.DISTANCE_BETWEEN_RACKS;

                Rack r = null;
                int rand = new Random().nextInt(100);
                if (rand < 80) {
                    r = new Rack(x, 0, z);
                }

                rackPositions[column][row] = new RackPosition(x, 0.01, z, r);
                if (r != null) r.setHolder(rackPositions[column][row]);
            }
        }
    }

//    private List<Task> createTasks(Robot robot) {
//
//        List<Task> tasks = new ArrayList<>();
//
//        List<Position> path = new ArrayList<>();
//        RackPosition finalPos = null;
//        RackPosition possibility = null;
//        while (finalPos == null)
//        {
//            int randomInt0 = new Random().nextInt(rackPositions.length);
//            int randomInt1 = new Random().nextInt(rackPositions[0].length);
//            possibility = rackPositions[randomInt0][randomInt1];
//            if (possibility.getAdjacentNode() != null && possibility.getRack() != null) finalPos = possibility;
//        }
//        Position surePos = finalPos.getAdjacentNode().getPosition();
//        Rack moving = possibility.getRack();
//        path = dijkstra.giveShortestPath(robot.getLatestNodePosition(), finalPos.getAdjacentNode());
//        GoToPosition goToPosition = new GoToPosition(path);
//
//        tasks.add(goToPosition);
//        tasks.add(new PickUpRack(possibility.getRack()));
//
//        finalPos = null;
//        possibility = null;
//        while (finalPos == null)
//        {
//            int randomInt0 = new Random().nextInt(rackPositions.length);
//            int randomInt1 = new Random().nextInt(rackPositions[0].length);
//            possibility = rackPositions[randomInt0][randomInt1];
//            if (possibility.getAdjacentNode() != null && possibility.getRack() == null) finalPos = possibility;
//        }
//        path = dijkstra.giveShortestPath(surePos, finalPos.getAdjacentNode());
//        goToPosition = new GoToPosition(path);
//        tasks.add(goToPosition);
//        tasks.add(new DropOffRack(moving, possibility));
//
//
//        return tasks;
//    }

    public List<Node> getNodes()
    {
        return this.dijkstra.getNodes();
    }


}

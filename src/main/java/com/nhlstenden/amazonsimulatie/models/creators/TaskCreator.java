package com.nhlstenden.amazonsimulatie.models.creators;

import com.nhlstenden.amazonsimulatie.models.Constants;
import com.nhlstenden.amazonsimulatie.models.Rack;
import com.nhlstenden.amazonsimulatie.models.RackPosition;
import com.nhlstenden.amazonsimulatie.models.Truck;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Node;
import com.nhlstenden.amazonsimulatie.models.tasks.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskCreator {

    public static Assignment createBringToTruckAssignment(RackPosition[][] rackPositions, Truck truck) {
        Assignment assignment = new Assignment();
        Rack rackToPickUp = null;
        RackPosition pickUpPosition = null;
        // TODO:  Remove the adjacent bit!!!
        Node adjacent = null;
        while (rackToPickUp == null || adjacent == null) {
            pickUpPosition = getRandomRackPosition(rackPositions);
            rackToPickUp = pickUpPosition.getRack();
            adjacent = pickUpPosition.getAdjacentNode();
        }
        assignment.addTask(new GoToPosition(pickUpPosition.getPosition()));
        assignment.addTask(new PickUpRack(rackToPickUp));
        assignment.addTask(new GoToPosition(truck.getDropOffPosition()));
        assignment.addTask(new DropOffRack(rackToPickUp, null));
        return assignment;
    }

    public static Assignment createBringToRackPositionAssignment(RackPosition[][] rackPositions, Truck truck) {
        Assignment assignment = new Assignment();

        // TODO: remove adjacent
        RackPosition randomRackPosition = getRandomRackPosition(rackPositions);
        Rack rack = randomRackPosition.getRack();
        Node adjacent = randomRackPosition.getAdjacentNode();

        while (rack != null ||  adjacent == null) {
            randomRackPosition = getRandomRackPosition(rackPositions);
            rack = randomRackPosition.getRack();
            adjacent = randomRackPosition.getAdjacentNode();
        }

        assignment.addTask(new GoToPosition(truck.getDropOffPosition()));
        assignment.addTask(new PickUpRack(new Rack(truck.getDropOffPosition())));
        // TODO: Finish the creator


        return assignment;
    }

    private static RackPosition getRandomRackPosition(RackPosition[][] rackPositions) {
        int randomHeight = new Random().nextInt(Constants.AMOUNT_OF_RACKS_HEIGHT);
        int randomWidth = new Random().nextInt(Constants.AMOUNT_OF_RACKS_WIDTH);
        return rackPositions[randomHeight][randomWidth];
    }


}

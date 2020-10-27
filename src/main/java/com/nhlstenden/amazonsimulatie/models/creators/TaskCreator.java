package com.nhlstenden.amazonsimulatie.models.creators;

import com.nhlstenden.amazonsimulatie.models.Constants;
import com.nhlstenden.amazonsimulatie.models.objects.Rack;
import com.nhlstenden.amazonsimulatie.models.objects.RackPosition;
import com.nhlstenden.amazonsimulatie.models.objects.Truck;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Node;
import com.nhlstenden.amazonsimulatie.models.tasks.*;

import java.util.Random;

/**
 * This class has some static functions that create Assignments for the robots the complete
 */
public class TaskCreator {

    /**
     * This static function returns an Assignment for a robot
     * It has to bring a Rack from the warehouse to the Truck
     * @param rackPositions Multidimensional array of RackPositions in the warehouse
     * @param truck The robot in driving in the warehouse
     * @return The assignment for the robots the complete
     */
    public static Assignment createBringToTruckAssignment(RackPosition[][] rackPositions, Truck truck) {
        Assignment assignment = new Assignment();
        Rack rackToPickUp = null;
        RackPosition pickUpPosition = null;
        // TODO:  Remove the adjacent bit!!!

        // First we make pick a random RackPosition that contains a Rack
        Node adjacent = null;
        while (rackToPickUp == null || adjacent == null || !rackToPickUp.isAvailable()) {
            pickUpPosition = getRandomRackPosition(rackPositions);
            rackToPickUp = pickUpPosition.getRack();
            adjacent = pickUpPosition.getAdjacentNode();
        }
        // Here we add different tasks to the Assignment
        // First it will go to the Node next to the RackPosition, then pick it up, after that drive to the truck and drop it.
        assignment.addTask(new GoToPosition(pickUpPosition.getAdjacentNode()));
        assignment.addTask(new PickUpRack(rackToPickUp));
        assignment.addTask(new GoToPosition(truck.getDropOffNode()));
        assignment.addTask(new DropOffRack(rackToPickUp, truck));
        return assignment;
    }

    /**
     * This static function returns an Assignment for a robot
     * It has to bring a Rack from the Truck to a RackPosition in the warehouse
     * @param rackPositions Multidimensional array of RackPositions in the warehouse
     * @param truck The robot in driving in the warehouse
     * @return The assignment for the robots the complete
     */
    public static Assignment createBringToRackPositionAssignment(RackPosition[][] rackPositions, Truck truck) {
        Assignment assignment = new Assignment();

        // TODO: remove adjacent

        // First take a DropOffPosition which doesn't have a Rack
        RackPosition dropOffPosition = getRandomRackPosition(rackPositions);
        while (dropOffPosition.getRack() != null || dropOffPosition.getAdjacentNode() == null) {
            dropOffPosition = getRandomRackPosition(rackPositions);
        }
        // Create a new Rack at the Truck
        Rack newRack = new Rack(truck);

        // First drive to the truck, then pick up the new Rack, drive to the dropOffPosition and drop the Rack off
        assignment.addTask(new GoToPosition(truck.getDropOffNode()));
        assignment.addTask(new PickUpRack(newRack));
        assignment.addTask(new GoToPosition(dropOffPosition.getAdjacentNode()));
        assignment.addTask(new DropOffRack(newRack, dropOffPosition));

        return assignment;
    }

    /**
     * Takes a multidimensional array of RackPositions and takes a random RackPosition from it
     * @param rackPositions The multidimensional array of the RackPositions
     * @return A random RackPosition
     */
    private static RackPosition getRandomRackPosition(RackPosition[][] rackPositions) {
        int randomHeight = new Random().nextInt(Constants.AMOUNT_OF_RACKS_HEIGHT);
        int randomWidth = new Random().nextInt(Constants.AMOUNT_OF_RACKS_WIDTH);
        return rackPositions[randomHeight][randomWidth];
    }


}

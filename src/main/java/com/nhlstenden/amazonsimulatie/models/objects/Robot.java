package com.nhlstenden.amazonsimulatie.models.objects;

import com.nhlstenden.amazonsimulatie.models.position.Position;
import com.nhlstenden.amazonsimulatie.models.warehousechanges.DropOffRackChange;
import com.nhlstenden.amazonsimulatie.models.warehousechanges.PickUpRackChange;
import com.nhlstenden.amazonsimulatie.models.warehousechanges.PositionChange;
import com.nhlstenden.amazonsimulatie.models.warehousechanges.WarehouseChange;
import com.nhlstenden.amazonsimulatie.models.objects.interfaces.CanHoldRacks;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Node;
import com.nhlstenden.amazonsimulatie.models.tasks.*;

import java.util.*;

/**
 * This is the Robot class. A robot drives around in the warehouse and moves Racks
 * The Robot class extends from MovableObject because it moves through the warehouse
 * Also it implements CanHoldRacks because it can moves the Racks
 */
public class Robot extends MovableObject implements CanHoldRacks {
    private Rack pickedUpRack = null;

    private final Queue<Assignment> assignments = new LinkedList<>();
    private Position latestNodePosition;
    private Assignment lastAssignment = null;

    /**
     * This constructor needs a beginNode the know what the starting position of the robot is
     * Also the latestNodePosition will be equal to the Position of the given Node
     * @param beginNode The starting Node of the Robot
     */
    public Robot(Node beginNode)
    {
        super(beginNode.getPosition());
        latestNodePosition = beginNode.getPosition();
    }

    /**
     * This function takes the first assignment of the Robot and completes a Task from it
     * @return a WarehouseChange if something changed in the World, else null
     */
    public WarehouseChange update()
    {
        WarehouseChange warehouseChange = null;
        if (!assignments.isEmpty()) {
            Assignment nextAssignment = assignments.peek();
            if (nextAssignment.getTask() instanceof DropOffRack) {
                warehouseChange = dropOffRack((DropOffRack) nextAssignment.getTask());
            }
            if (nextAssignment.getTask() instanceof PickUpRack) {
                warehouseChange = pickUpRack((PickUpRack) nextAssignment.getTask());
            }
            if (nextAssignment.getTask() instanceof GoToPosition) {
                warehouseChange = goToPosition((GoToPosition) nextAssignment.getTask(), nextAssignment);
            }
            if (warehouseChange instanceof DropOffRackChange || warehouseChange instanceof PickUpRackChange)
                nextAssignment.removeTask();
            if (nextAssignment.isFinished()) lastAssignment = assignments.poll();
        }
        return warehouseChange;
    }

    /**
     * Adds an assignment to the Robot
     * @param assignment Assignment to add
     */
    public void addAssignment(Assignment assignment) {
        this.assignments.add(assignment);
    }

    /**
     * Takes the last Position of the goToPositionTask and drives towards it.
     * When it reaches the Position it removes it from the task
     * @param goToPositionTask The Task which has to be completed
     * @param assignment The assignment that being executed
     * @return A WarehouseChange if something is changed
     */
    private WarehouseChange goToPosition(GoToPosition goToPositionTask, Assignment assignment) {

        /* If the getNextPosition == null the Position is already reached
        So we'll remove the task and return null */
        if (goToPositionTask.getNextPosition() == null) {
            assignment.removeTask();
            return null;
        }

        Position goToPosition = goToPositionTask.getNextPosition();

        // Calculate how much the robot has to move on every axis
        double differenceX = goToPosition.getX() - position.getX();
        double differenceY = goToPosition.getY() - position.getY();
        double differenceZ = goToPosition.getZ() - position.getZ();

        // Change the Position
        position.x += positionDifference(differenceX);
        position.y += positionDifference(differenceY);
        position.z += positionDifference(differenceZ);

        // If the goToPosition is reached the latestNodePosition will be updated
        // If the whole Task is completed we'll remove it from the tasks Queue
        if (goToPosition.getX() == position.getX() && goToPosition.getY() == position.getY() && goToPosition.getZ() == position.getZ()) {
            latestNodePosition = goToPositionTask.removeNextPosition();
            if (goToPositionTask.isFinished()) assignment.removeTask();
        }

        return new PositionChange(this);
    }

    /**
     * This function will calculate how much the Robot has to move.
     * This will be calculated with the difference between the Position given in the parameters.
     * @param difference Difference between the two Positions
     * @return The amount the Robot has to move
     */
    private double positionDifference(double difference)
    {
        if (difference > 0)
            return Math.min(difference, 0.2);
        if (difference < 0)
            if (difference < 0.2) return -0.2;
            else return difference;
        return 0;
    }

    /**
     * This function will pick up a Rack and change the values of the Rack and Robot
     * After this it returns a WarehouseChange
     * @param pickOffRackTask The PickUpRack Task which has to be performed
     * @return A WarehouseChange filled with the data of what happened
     */
    private WarehouseChange pickUpRack(PickUpRack pickOffRackTask)
    {
        Rack rack = pickOffRackTask.getRackToPickUp();
        setRack(rack);
        if (pickOffRackTask.getRackToPickUp().getHolder() != null)
            pickOffRackTask.getRackToPickUp().getHolder().setRack(null);
        rack.setHolder(this);

        return new PickUpRackChange(this, rack);
    }

    /**
     * This function will drop off a Rack and change the values of the Rack, Robot and Position where it will be dropped
     * After this it returns a WarehouseChange
     * @param dropOffRackTask The DropOffRack Task which has to be performed
     * @return The WarehouseChange with the data of the Task
     */
    private WarehouseChange dropOffRack(DropOffRack dropOffRackTask)
    {
        Rack r = dropOffRackTask.getRackToDropOff();
        dropOffRackTask.getDropOffPosition().setRack(r);
        r.setHolder(dropOffRackTask.getDropOffPosition());
        setRack(null);

        return new DropOffRackChange(this, dropOffRackTask.getDropOffPosition());
    }

    /**
     * Returns boolean which indicates if the Robot has finished all the Assignments
     * @return boolean which is true if all Assignments are finished
     */
    public boolean finishedAllAssignments() {
        return assignments.isEmpty();
    }

    /**
     * Returns the node which the Robot visited the last
     * @return The last visited Node
     */
    public Position getLatestNodePosition() {
        return latestNodePosition;
    }

    /**
     * return The last finished Assignment
     */
    public Assignment getLastAssignment() {
        return lastAssignment;
    }

    /**
     * Sets the picked up Rack of the Robot
     * @param rack Rack that is picked up
     */
    @Override
    public void setRack(Rack rack) {
        this.pickedUpRack = rack;
    }

    /**
     * Returns the Rack that the Robot is carrying right now
     * @return The Rack that the Robot carries
     */
    @Override
    public Rack getRack() {
        return this.pickedUpRack;
    }

    /**
     * Returns the type of the Robot, this can be used by the client to know what object it has to spawn
     * @return The type of the Robot
     */
    @Override
    public String getType() {
        return Robot.class.getSimpleName().toLowerCase();
    }
}
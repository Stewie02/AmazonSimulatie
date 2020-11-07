package com.nhlstenden.amazonsimulatie.models.objects;

import com.nhlstenden.amazonsimulatie.models.position.RealPosition;
import com.nhlstenden.amazonsimulatie.models.warehousechanges.PositionChange;
import com.nhlstenden.amazonsimulatie.models.warehousechanges.WarehouseChange;
import com.nhlstenden.amazonsimulatie.models.objects.interfaces.CanHoldRacks;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Node;
import com.nhlstenden.amazonsimulatie.models.tasks.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The truck drives outside the warehouse and gives the Robots different tasks
 */
public class Truck extends MovableObject implements CanHoldRacks {

    private final RealPosition storagePosition = new RealPosition(2.0, -1.55, -7.0);
    private final RealPosition getNewTaskPosition = new RealPosition(2.0, -1.55, -19);

    private final Queue<Assignment> assignmentsForRobots = new LinkedList<>();
    private final List<Assignment> givenAssignments = new ArrayList<>();
    private final Node dropOffNode;

    /**
     * This constructor needs the drop off Node of the Truck
     * This is later used to know where the Robots need to drive to
     * @param dropOffNode The Node where the Robot drops the Racks
     */
    public Truck(Node dropOffNode)
    {
        super(2.0, -1.55, -19);
        this.dropOffNode = dropOffNode;
    }

    /**
     * This function updates state of the Robot
     * It takes the first Assignment from the Queue and runs it
     * @return A WarehouseChange of what exactly happened
     */
    @Override
    public WarehouseChange update() {
        // If the Truck still has Assignments and is at the storagePosition...
        if (!allAssignmentsAreRedeemed() && positionMatch(position, storagePosition)) {
            return null;
        }
        // If the Truck has Assignments and not at the storagePosition the Truck will drive to it
        if (!allAssignmentsAreRedeemed() && !positionMatch(position, storagePosition)) {
            driveForward();
        }
        // If the Truck has no Assignments and not at the Position to get new Tasks it will drive back
        if (allAssignmentsAreRedeemed() && !positionMatch(position, getNewTaskPosition)) {
            driveBackward();
        }
        return new PositionChange(this);
    }

    /**
     * The robot will drive forward
     */
    private void driveForward() {
        this.position.setZ(position.getZ() + 0.2);
    }

    /**
     * The robot will drive backwards
     */
    private void driveBackward() {
        this.position.setZ(position.getZ() - 0.2);
    }

    /**
     * The Truck will return the first Assignment from the Queue
     * If it isn't null we'll add it to givenAssignments
     * @return The next Assignment
     */
    public Assignment getNextAssignment() {
        Assignment a = assignmentsForRobots.poll();
        if (a != null)
            givenAssignments.add(a);
        return a;
    }

    /**
     * Returns true if all assignments are redeemed
     * @return boolean based on if the are Assignments left
     */
    private boolean allAssignmentsAreRedeemed() {
        return givenAssignments.isEmpty() && assignmentsForRobots.isEmpty();
    }

    /**
     * Returns true if there are still Assignments in the Truck and Truck is at the storagePosition
     * @return true if there are assignments available
     */
    public boolean assignmentsAvailable() {
        return !assignmentsForRobots.isEmpty() && positionMatch(position, storagePosition);
    }

    /**
     * Returns true if Assignments can be added.
     * Assignments can be added when all Assignments are redeemed and the Position is equal to the getNewTaskPosition
     * @return true if it's time to add Assignments
     */
    public boolean timeToAddAssignments() {
        return allAssignmentsAreRedeemed() && positionMatch(position, getNewTaskPosition);
    }

    /**
     * Removes the assignment from the given Assignments
     * @param assignment Assignment to remove
     */
    public void redeemAssignment(Assignment assignment) {
        givenAssignments.remove(assignment);
    }

    /**
     * Give a List of Assignments to add to the Truck
     * @param assignments List of Assignments to add
     */
    public void addAssignments(List<Assignment> assignments) {
        this.assignmentsForRobots.addAll(assignments);
    }

    /**
     * Check if the first Position has the same X, Y and Z value as the second Position
     * @param pos1 First Position
     * @param pos2 Second Position
     * @return true if the values are equal
     */
    private boolean positionMatch(RealPosition pos1, RealPosition pos2) {
        return pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY() && pos1.getZ() == pos2.getZ();
    }

    /**
     * Returns the drop off Node of the Truck
     * @return the drop off Node
     */
    public Node getDropOffNode() {
        return this.dropOffNode;
    }

    /**
     * Returns the type of the Truck as an String
     * @return The type
     */
    @Override
    public String getType() {
        return Truck.class.getSimpleName().toLowerCase();
    }

    /**
     * Returns the Rack the Truck is carrying
     * For the Truck this is always null because when the Truck 'picks' one up the Rack will be removed
     * @return always null
     */
    @Override
    public Rack getRack() {
        return null;
    }

    /**
     * For the Truck nothing will happen because in the simulation the Truck can't pick up Rack
     * @param rack The Rack that needs to be set
     */
    public void setRack(Rack rack) {}
}
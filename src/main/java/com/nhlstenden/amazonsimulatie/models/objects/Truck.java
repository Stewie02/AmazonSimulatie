package com.nhlstenden.amazonsimulatie.models.objects;

import com.nhlstenden.amazonsimulatie.models.position.RealPosition;
import com.nhlstenden.amazonsimulatie.models.worldchanges.PositionChange;
import com.nhlstenden.amazonsimulatie.models.worldchanges.WorldChange;
import com.nhlstenden.amazonsimulatie.models.objects.interfaces.CanHoldRacks;
import com.nhlstenden.amazonsimulatie.models.objects.interfaces.Object3D;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Node;
import com.nhlstenden.amazonsimulatie.models.tasks.*;

import java.util.*;

public class Truck extends MovableObject implements Object3D, CanHoldRacks {

    private final RealPosition storagePosition = new RealPosition(2.0, -1.55, -7.0);
    private final RealPosition getNewTaskPosition = new RealPosition(2.0, -1.55, -19);

    private final Queue<Assignment> assignmentsForRobots = new LinkedList<>();
    private final List<Assignment> givenAssignments = new ArrayList<>();
    private final RackPosition[][] rackPositions;
    private final Node dropOffNode;

    public Truck(RackPosition[][] rackPositions, Node dropOffNode)
    {
        super(2.0, -1.55, -19);
        this.rackPositions = rackPositions;
        this.dropOffNode = dropOffNode;
    }

    @Override
    public WorldChange update() {
        if (!allAssignmentsAreRedeemed() && positionMatch(position, storagePosition)) {
            return null;
        }
        //
        if (!allAssignmentsAreRedeemed() && !positionMatch(position, storagePosition)) {
            driveForward();
        }
        if (allAssignmentsAreRedeemed() && !positionMatch(position, getNewTaskPosition)) {
            driveBackward();
        }
        return new PositionChange(this);
    }

    private void driveForward() {
        this.position.setZ(position.getZ() + 0.2);
    }

    private void driveBackward() {
        this.position.setZ(position.getZ() - 0.2);
    }

    public Assignment getNextAssignment() {
        Assignment a = assignmentsForRobots.poll();
        if (a != null)
            givenAssignments.add(a);
        return a;
    }

    private boolean allAssignmentsAreRedeemed() {
        return givenAssignments.isEmpty() && assignmentsForRobots.isEmpty();
    }

    public boolean assignmentsAvailable() {
        return !assignmentsForRobots.isEmpty() && positionMatch(position, storagePosition);
    }

    public boolean timeToAddAssignments() {
        return allAssignmentsAreRedeemed() && positionMatch(position, getNewTaskPosition);
    }

    public boolean redeemAssignment(Assignment assignment) {
        return givenAssignments.remove(assignment);
    }

    public void addAssignments(List<Assignment> assignments) {
        this.assignmentsForRobots.addAll(assignments);
    }


    private boolean positionMatch(RealPosition pos1, RealPosition pos2) {
        return pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY() && pos1.getZ() == pos2.getZ();
    }

    public Node getDropOffNode() {
        return this.dropOffNode;
    }

    @Override
    public String getType() {
        return Truck.class.getSimpleName().toLowerCase();
    }

    @Override
    public Rack getRack() {
        return null;
    }

    public void setRack(Rack rack) {

    }
}
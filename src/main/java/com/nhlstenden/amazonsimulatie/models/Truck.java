package com.nhlstenden.amazonsimulatie.models;

import com.nhlstenden.amazonsimulatie.models.WorldChanges.PositionChange;
import com.nhlstenden.amazonsimulatie.models.WorldChanges.WorldChange;
import com.nhlstenden.amazonsimulatie.models.tasks.*;

import java.util.*;

public class Truck extends MovableObject implements Object3D {

    private final Position storagePosition = new Position(2.0, -1.55, -7.0);
    private final Position getNewTaskPosition = new Position(2.0, -1.55, -19);

    private final Queue<Assignment> assignmentsForRobots = new LinkedList<>();
    private final List<Assignment> givenAssignments = new ArrayList<>();
    private final RackPosition[][] rackPositions;
    private final Position dropOffPosition;

    public Truck(RackPosition[][] rackPositions, Position dropOffPosition)
    {
        super(2.0, -1.55, -19);
        this.rackPositions = rackPositions;
        this.dropOffPosition = dropOffPosition;
    }

    @Override
    public WorldChange update() {
        if (!assignmentsForRobots.isEmpty() && positionMatch(position, storagePosition)) {
            return null;
        }
        if (assignmentsForRobots.isEmpty() && !positionMatch(position, getNewTaskPosition)) {
            driveBackward();
        }
        if (assignmentsForRobots.isEmpty() && positionMatch(position, getNewTaskPosition)) {
            // New assignments will be created.
            return null;
        }
        if (!assignmentsForRobots.isEmpty() && !positionMatch(position, storagePosition)) {
            driveForward();
        }
        return new PositionChange(this);
    }

    private void driveForward() {
        this.position.setZ(position.getZ() + 0.2);
    }

    private void driveBackward() {
        this.position.setZ(position.getZ() - 0.2);
    }

    public Assignment getNextTask() {
        Assignment a = assignmentsForRobots.poll();
        givenAssignments.add(a);
        return a;
    }

    public boolean tasksAvailable() {
        return !assignmentsForRobots.isEmpty() && positionMatch(position, storagePosition);
    }

    public boolean redeemAssignment(Assignment assignment) {
        return givenAssignments.remove(assignment);
    }

    public void addAssignments(List<Assignment> assignments) {
        this.assignmentsForRobots.addAll(assignments);
    }


    private boolean positionMatch(Position pos1, Position pos2) {
        return pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY() && pos1.getZ() == pos2.getZ();
    }

    public Position getDropOffPosition() {
        return this.dropOffPosition;
    }

    @Override
    public String getType() {
        return Truck.class.getSimpleName().toLowerCase();
    }
}
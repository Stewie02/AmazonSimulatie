package com.nhlstenden.amazonsimulatie.models;

import com.nhlstenden.amazonsimulatie.models.WorldChanges.PositionChange;
import com.nhlstenden.amazonsimulatie.models.WorldChanges.WorldChange;
import com.nhlstenden.amazonsimulatie.models.tasks.DropOffRack;
import com.nhlstenden.amazonsimulatie.models.tasks.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Truck extends MovableObject implements Object3D {

    private final Position storagePosition = new Position(2.0, -1.55, -7.0);
    private final Position getNewTaskPosition = new Position(2.0, -1.55, -19);

    private final Queue<Task> tasksForRobots = new LinkedList<>();
    private final List<Task> givenTasks = new ArrayList<>();

    public Truck()
    {
        super(2.0, -1.55, -19);
    }

    @Override
    public WorldChange update() {
        if (!tasksForRobots.isEmpty() && positionMatch(position, storagePosition)) {
            return null;
        }
        if (tasksForRobots.isEmpty() && !positionMatch(position, getNewTaskPosition)) {
            driveBackward();
        }
        if (tasksForRobots.isEmpty() && positionMatch(position, getNewTaskPosition)) {
            generateNewTasks();
            return null;
        }
        if (!tasksForRobots.isEmpty() && !positionMatch(position, storagePosition)) {
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

    private void generateNewTasks() {
        for (int i = 0; i < 4; i++)
        {
            tasksForRobots.add(new DropOffRack(null, null));
        }
    }

    public Task getNextTask() {
        Task t = tasksForRobots.poll();
        givenTasks.add(t);
        return t;
    }

    public boolean tasksAvailable() {
        return !tasksForRobots.isEmpty() && positionMatch(position, storagePosition);
    }

    public boolean redeemTask(Task task) {
        return givenTasks.remove(task);
    }


    private boolean positionMatch(Position pos1, Position pos2) {
        return pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY() && pos1.getZ() == pos2.getZ();
    }


    @Override
    public String getType() {
        return Truck.class.getSimpleName().toLowerCase();
    }
}
package com.nhlstenden.amazonsimulatie.models.tasks;

import com.nhlstenden.amazonsimulatie.models.Position;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GoToPosition implements Task {

    private final Queue<Position> positionQueue = new LinkedList<>();

    public GoToPosition(List<Position> positionList) {
        this.positionQueue.addAll(positionList);
    }

    public Position getNextPosition() {
        return positionQueue.peek();
    }

    public Position removeNextPosition() {
        return positionQueue.poll();
    }

    public boolean isFinished() {
        return positionQueue.isEmpty();
    }

    @Override
    public String getType() {
        return GoToPosition.class.getSimpleName();
    }

}

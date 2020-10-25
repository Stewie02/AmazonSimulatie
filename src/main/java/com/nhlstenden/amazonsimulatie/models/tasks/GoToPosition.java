package com.nhlstenden.amazonsimulatie.models.tasks;

import com.nhlstenden.amazonsimulatie.models.Position;
import com.sun.org.apache.bcel.internal.generic.GotoInstruction;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GoToPosition implements Task {

    private final Queue<Position> positionQueue = new LinkedList<>();
    private final Position finalPosition;

//    public GoToPosition(List<Position> positionList) {
//        this.positionQueue.addAll(positionList);
//    }

    public GoToPosition(Position finalPosition) {
        this.finalPosition = finalPosition;
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

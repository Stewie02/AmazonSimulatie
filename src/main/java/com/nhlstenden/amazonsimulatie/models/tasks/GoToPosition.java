package com.nhlstenden.amazonsimulatie.models.tasks;

import com.nhlstenden.amazonsimulatie.models.position.Position;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GoToPosition implements Task {

    private final Queue<Position> positionQueue = new LinkedList<>();
    private final Node finalNode;

//    public GoToPosition(List<Position> positionList) {
//        this.positionQueue.addAll(positionList);
//    }

    public GoToPosition(Node finalNode) {
        this.finalNode = finalNode;
    }

    public Node getFinalNode() {
        return this.finalNode;
    }

    public void setRoute(List<Position> route) {
        positionQueue.addAll(route);
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

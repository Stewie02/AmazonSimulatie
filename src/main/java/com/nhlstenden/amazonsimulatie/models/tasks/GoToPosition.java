package com.nhlstenden.amazonsimulatie.models.tasks;

import com.nhlstenden.amazonsimulatie.models.position.Position;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The GoToPosition class is a Task with all the information needed for the Robot to move
 */
public class GoToPosition implements Task {

    private final Queue<Position> positionQueue = new LinkedList<>();
    private final Node finalNode;

    /**
     * Gets the final node and creates the Task
     * @param finalNode The node of what the goal is
     */
    public GoToPosition(Node finalNode) {
        this.finalNode = finalNode;
    }

    /**
     * Returns the final node of the Task
     * @return The final node
     */
    public Node getFinalNode() {
        return this.finalNode;
    }

    /**
     * Set the Route of how the drive
     * @param route The route to drive
     */
    public void setRoute(List<Position> route) {
        positionQueue.addAll(route);
    }

    /**
     * Returns the Position needed to visit next
     * @return Position which indicates where the drive next
     */
    public Position getNextPosition() {
        return positionQueue.peek();
    }

    /**
     * Remove the next Position
     * Can be used when the Position is reached
     * @return The removed Position
     */
    public Position removeNextPosition() {
        return positionQueue.poll();
    }

    /**
     * Returns boolean based on the fact if the whole route is completed
     * @return true if the whole route is completed
     */
    public boolean isFinished() {
        return positionQueue.isEmpty();
    }

}

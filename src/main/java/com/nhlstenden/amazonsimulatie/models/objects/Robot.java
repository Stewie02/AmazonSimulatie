package com.nhlstenden.amazonsimulatie.models.objects;

import com.nhlstenden.amazonsimulatie.models.Position;
import com.nhlstenden.amazonsimulatie.models.WorldChanges.DropOffRackChange;
import com.nhlstenden.amazonsimulatie.models.WorldChanges.PickUpRackChange;
import com.nhlstenden.amazonsimulatie.models.WorldChanges.PositionChange;
import com.nhlstenden.amazonsimulatie.models.WorldChanges.WorldChange;
import com.nhlstenden.amazonsimulatie.models.objects.interfaces.CanHoldRacks;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Node;
import com.nhlstenden.amazonsimulatie.models.tasks.*;

import java.util.*;

/*
 * Deze class stelt een robot voor. Hij impelementeerd de class Object3D, omdat het ook een
 * 3D object is. Ook implementeerd deze class de interface Updatable. Dit is omdat
 * een robot geupdate kan worden binnen de 3D wereld om zich zo voort te bewegen.
 */
public class Robot extends MovableObject implements CanHoldRacks {
    private Rack pickedUpRack = null;

    private final Queue<Assignment> assignments = new LinkedList<>();
    private Position latestNodePosition;
    private Assignment lastAssignment = null;

    public Robot(Node beginNode)
    {
        super(beginNode.getPosition());
        latestNodePosition = beginNode.getPosition();
    }

    public Robot(double x, double y, double z, Rack pickedUpRack) {
        super(x, y, z);
        this.pickedUpRack = pickedUpRack;
    }

    /*
     * Deze update methode wordt door de World aangeroepen wanneer de
     * World zelf geupdate wordt. Dit betekent dat elk object, ook deze
     * robot, in de 3D wereld steeds een beetje tijd krijgt om een update
     * uit te voeren. In de updatemethode hieronder schrijf je dus de code
     * die de robot steeds uitvoert (bijvoorbeeld positieveranderingen). Wanneer
     * de methode true teruggeeft (zoals in het voorbeeld), betekent dit dat
     * er inderdaad iets veranderd is en dat deze nieuwe informatie naar de views
     * moet worden gestuurd. Wordt false teruggegeven, dan betekent dit dat er niks
     * is veranderd, en de informatie hoeft dus niet naar de views te worden gestuurd.
     * (Omdat de informatie niet veranderd is, is deze dus ook nog steeds hetzelfde als
     * in de view)
     */
    public WorldChange update()
    {
        WorldChange worldChange = null;
        if (!assignments.isEmpty()) {
            Queue<Task> tasks = assignments.peek().getTasks();
            if (tasks.peek() instanceof DropOffRack) {
                worldChange = dropOffRack((DropOffRack) tasks.peek());
            }
            if (tasks.peek() instanceof PickUpRack) {
                worldChange = pickUpRack((PickUpRack) tasks.peek());
            }
            if (tasks.peek() instanceof GoToPosition) {
                worldChange = goToPosition((GoToPosition) tasks.peek(), tasks);
            }
            if (worldChange instanceof DropOffRackChange || worldChange instanceof PickUpRackChange)
                tasks.poll();
            if (tasks.isEmpty()) lastAssignment = assignments.poll();
        }
        return worldChange;
    }

    public void addAssignment(Assignment assignment) {
        this.assignments.add(assignment);
    }

    private WorldChange goToPosition(GoToPosition goToPositionTask, Queue<Task> tasks) {

        // Speed of robot is 0.2
        if (goToPositionTask.getNextPosition() == null) {
            tasks.poll();
            return null;
        }

        // Here happens the movement of the robot
        Position goToPosition = goToPositionTask.getNextPosition();

        double differenceX = goToPosition.x - position.x;
        double differenceY = goToPosition.y - position.y;
        double differenceZ = goToPosition.z - position.z;

        position.x += positionDifference(differenceX);
        position.y += positionDifference(differenceY);
        position.z += positionDifference(differenceZ);

        if (goToPosition.getX() == position.getX() && goToPosition.getY() == position.getY() && goToPosition.getZ() == position.getZ()) {
            latestNodePosition = goToPositionTask.removeNextPosition();
            if (goToPositionTask.isFinished()) tasks.poll();
        }
        return new PositionChange(this);
    }

    private double positionDifference(double dif)
    {
        if (dif > 0)
            if (dif > 0.2) return 0.2;
            else return dif;
        if (dif < 0)
            if (dif < 0.2) return -0.2;
            else return dif;
        return 0;
    }

    private WorldChange pickUpRack(PickUpRack pickOffRackTask)
    {
        Rack rack = pickOffRackTask.getRackToPickUp();
        setRack(rack);
        if (pickOffRackTask.getRackToPickUp().getHolder() != null)
            pickOffRackTask.getRackToPickUp().getHolder().setRack(null);
        rack.setHolder(this);
        rack.setAvailability(false);

        return new PickUpRackChange(this, rack);
    }

    private WorldChange dropOffRack(DropOffRack dropOffRackTask)
    {
        Rack r = this.pickedUpRack;
        setRack(null);
        dropOffRackTask.getDropOffPosition().setRack(r);
        r.setAvailability(true);
        r.setHolder(dropOffRackTask.getDropOffPosition());

        return new DropOffRackChange(this, dropOffRackTask.getDropOffPosition(), r);
    }

    public boolean finishedAllAssignments() {
        return assignments.isEmpty();
    }

    public Position getLatestNodePosition() {
        return latestNodePosition;
    }

    public Assignment getLastAssignment() {
        return lastAssignment;
    }

    public void setRack(Rack rack) {
        this.pickedUpRack = rack;
    }

    public Rack getRack() {
        return this.pickedUpRack;
    }

    @Override
    public String getType() {
        /*
         * Dit onderdeel wordt gebruikt om het type van dit object als stringwaarde terug
         * te kunnen geven. Het moet een stringwaarde zijn omdat deze informatie nodig
         * is op de client, en die verstuurd moet kunnen worden naar de browser. In de
         * javascript code wordt dit dan weer verder afgehandeld.
         */
        return Robot.class.getSimpleName().toLowerCase();
    }
}
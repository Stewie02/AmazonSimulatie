package com.nhlstenden.amazonsimulatie.models;

import java.util.*;

/*
 * Deze class stelt een robot voor. Hij impelementeerd de class Object3D, omdat het ook een
 * 3D object is. Ook implementeerd deze class de interface Updatable. Dit is omdat
 * een robot geupdate kan worden binnen de 3D wereld om zich zo voort te bewegen.
 */
class Robot extends MovableObject implements Object3D, CanHoldRacks {
    private Rack pickedUpRack = null;

    private Queue<Position> positionsToVisit = new LinkedList<>();

    public Robot(double x, double y, double z) {
        super(x, y, z);
    }

    public Robot(Position pos)
    {
        super(pos);
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
    public boolean update()
    {
        // Speed of robot is 0.2
        if (positionsToVisit.isEmpty()) return false;

        // Here happens the movement of the robot
        Position goToPosition = positionsToVisit.peek();

        double differenceX = goToPosition.x - position.x;
        double differenceY = goToPosition.y - position.y;
        double differenceZ = goToPosition.z - position.z;

        position.x += positionDifference(differenceX);
        position.y += positionDifference(differenceY);
        position.z += positionDifference(differenceZ);

        if (goToPosition.x == position.x && goToPosition.y == position.y && goToPosition.z == position.z) positionsToVisit.poll();
        return true;
    }

    private double positionDifference(double dif)
    {
        if (dif > 0)
            if (dif >= 0.2) return 0.2;
            else return dif;
        if (dif < 0)
            if (dif <= 0.2) return -0.2;
            else return dif;
        return 0;
    }

    public void pickUpRack(Rack rack)
    {
        rack.setPosition(position.x, position.y, position.z);
        this.pickedUpRack = rack;

    }

    public Rack dropOffRack()
    {
        Rack r = this.pickedUpRack;
        this.pickedUpRack = null;
        return r;
    }

    public void goToPosition(List<Position> goToPositions)
    {
        positionsToVisit.addAll(goToPositions);
        System.out.println("Hahah" + positionsToVisit.size());
    }

    public boolean hasReachedPosition() {
        return positionsToVisit.isEmpty();
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
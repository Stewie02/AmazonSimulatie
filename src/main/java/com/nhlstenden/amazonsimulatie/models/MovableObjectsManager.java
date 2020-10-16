package com.nhlstenden.amazonsimulatie.models;

import com.nhlstenden.amazonsimulatie.models.PathFinding.Dijkstra;
import com.nhlstenden.amazonsimulatie.models.PathFinding.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MovableObjectsManager {

    private final RackPosition[][] rackPositions;
    private final Robot[] robots;
    private final Truck truck;
    private final List<Node> nodeList;
    private final Dijkstra dijkstra = new Dijkstra();

    public MovableObjectsManager()
    {
        robots = new Robot[2];
        fillRobotArray();

        rackPositions = new RackPosition[8][8];
        fillRackPositionArray();

        nodeList = new ArrayList<>();
        createNodes();

        truck = new Truck(2, -1.55, -7.1);
    }

    // TODO: Add the truck
    public List<Object3D> getMovableObjectsAsList()
    {
        List<Object3D> allObjects = new ArrayList<>();

        for (MovableObject robot : robots)
            allObjects.add(new ProxyObject3D((Object3D) robot));

        allObjects.add(new ProxyObject3D(truck));
        return allObjects;
    }

    public List<RackPosition> getRackPositions()
    {
        List<RackPosition> rackPositionsList = new ArrayList<>();
        for (RackPosition[] innerRackPositions : rackPositions)
            rackPositionsList.addAll(Arrays.asList(innerRackPositions));
        return rackPositionsList;
    }

    public List<Object3D> update()
    {
        List<Object3D> changedObjects = new ArrayList<>();
        for (MovableObject robot : robots)
        {
            if (robot.update())
                changedObjects.add(new ProxyObject3D((Object3D)robot));
        }
        return changedObjects;
    }


    private void fillRobotArray()
    {
        for (int i = 0; i < robots.length; i++) {
            robots[i] = new Robot(i * 1.5, 0, 0);
        }

        List<Position> pos1 = new ArrayList<>();
        List<Position> pos2 = new ArrayList<>();
        pos1.add(new Position(100, 0, 0));
        pos2.add(new Position(0, 100, 0));
        robots[0].goToPosition(pos2);
        robots[1].goToPosition(pos1);
    }

    private void fillRackPositionArray()
    {
        double x, z = 0;
        for (int column = 0; column < rackPositions.length; column++)
        {
            x = 0;
            if (column % 2 == 0 && column != 0) z += 2.4;
            else if(column != 0) z += 1.2;

            for (int row = 0; row < rackPositions[column].length; row++)
            {
                if (row % 2 == 0 && row != 0) x += 2.4;
                else if(row != 0) x += 1.2;

                Rack r = null;
                int rand = new Random().nextInt(100);
                if (rand < 80) r = new Rack(x, 0, z);

                rackPositions[column][row] = new RackPosition(x, 0.01, z, r);
            }
        }
    }

    private void createNodes()
    {
        // TODO: implementation functionality

    }


}

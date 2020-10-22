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
    private final Dijkstra dijkstra;

    public MovableObjectsManager()
    {
        rackPositions = new RackPosition[Constants.AMOUNT_OF_RACKS_HEIGHT][Constants.AMOUNT_OF_RACKS_WIDTH];
        fillRackPositionArray();

        dijkstra = new Dijkstra(NodeListCreator.createNodeList(rackPositions));

        robots = new Robot[1];
        fillRobotArray();

        truck = new Truck(2, -1.55, -7);
    }

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
        for (MovableObject object : robots)
        {
            if (object.update())
                changedObjects.add(new ProxyObject3D((Object3D)object));
            if (object instanceof Robot)
                if (((Robot) object).hasReachedPosition())
                    ((Robot) object).goToPosition(dijkstra.giveShortestPath(((Robot) object).getLatestNodePosition(), dijkstra.getNodes().get(new Random().nextInt(dijkstra.getNodes().size()))));
        }
        if (truck.update())
            changedObjects.add(new ProxyObject3D((Object3D)truck));
        return changedObjects;
    }


    private void fillRobotArray()
    {
        for (int i = 0; i < robots.length; i++) {
            robots[i] = new Robot(dijkstra.getNodes().get(0).getPosition());
        }
    }

    private void fillRackPositionArray()
    {
        double x, z = 0;
        for (int column = 0; column < rackPositions.length; column++)
        {
            x = 0;
            if (column % 2 == 0 && column != 0) z += Constants.DISTANCE_BETWEEN_RACK_WITH_PATH;
            else if(column != 0) z += Constants.DISTANCE_BETWEEN_RACKS;

            for (int row = 0; row < rackPositions[column].length; row++)
            {
                if (row % 2 == 0 && row != 0) x += Constants.DISTANCE_BETWEEN_RACK_WITH_PATH;
                else if(row != 0) x += Constants.DISTANCE_BETWEEN_RACKS;

                Rack r = null;
                int rand = new Random().nextInt(100);
                if (rand < 80) r = new Rack(x, 0, z);

                rackPositions[column][row] = new RackPosition(x, 0.01, z, r);
                if (r != null) r.setHolder(rackPositions[column][row]);
            }
        }
    }

    public List<Node> getNodes()
    {
        return this.dijkstra.getNodes();
    }


}

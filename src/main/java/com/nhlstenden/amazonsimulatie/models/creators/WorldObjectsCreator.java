package com.nhlstenden.amazonsimulatie.models.creators;

import com.nhlstenden.amazonsimulatie.models.Constants;
import com.nhlstenden.amazonsimulatie.models.objects.Rack;
import com.nhlstenden.amazonsimulatie.models.objects.RackPosition;
import com.nhlstenden.amazonsimulatie.models.objects.Robot;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Dijkstra;

import java.util.Random;

public  class WorldObjectsCreator {

    public static void createRackPositions(RackPosition[][] rackPositions) {

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
                if (rand < 80) {
                    r = new Rack(x, 0, z);
                }

                rackPositions[column][row] = new RackPosition(x, 0.01, z, r);
                if (r != null) r.setHolder(rackPositions[column][row]);
            }
        }
    }

    public static void createRobots(Robot[] robots, Dijkstra dijkstra) {
        for (int i = 0; i < robots.length; i++) {
            robots[i] = new Robot(dijkstra.getNodes().get(i));
        }
    }


}

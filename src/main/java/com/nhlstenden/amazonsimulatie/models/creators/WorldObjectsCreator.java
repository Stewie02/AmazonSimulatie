package com.nhlstenden.amazonsimulatie.models.creators;

import com.nhlstenden.amazonsimulatie.models.Constants;
import com.nhlstenden.amazonsimulatie.models.objects.Rack;
import com.nhlstenden.amazonsimulatie.models.objects.RackPosition;
import com.nhlstenden.amazonsimulatie.models.objects.Robot;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Node;

import java.util.List;
import java.util.Random;

/**
 * This class contains some static function to create objects in the world
 */
public class WorldObjectsCreator {

    /**
     * This function takes in a multidimensional array of RackPositions and fills it with just created RackPositions
     * @param rackPositions the multidimensional array of RackPositions that needs to be filled
     */
    public static void createRackPositions(RackPosition[][] rackPositions) {

        double x, z = 0;
        // Loop though the array of RackPositions the create new RackPositions
        for (int column = 0; column < rackPositions.length; column++)
        {
            x = 0;
            if (column % 2 == 0 && column != 0) z += Constants.DISTANCE_BETWEEN_RACK_WITH_PATH;
            else if(column != 0) z += Constants.DISTANCE_BETWEEN_RACKS;

            for (int row = 0; row < rackPositions[column].length; row++)
            {
                if (row % 2 == 0 && row != 0) x += Constants.DISTANCE_BETWEEN_RACK_WITH_PATH;
                else if(row != 0) x += Constants.DISTANCE_BETWEEN_RACKS;

                rackPositions[column][row] = new RackPosition(x, 0.01, z);

                // We want 75% change of to create a Rack
                Rack r = null;
                int rand = new Random().nextInt(100);
                if (rand < 80) {
                    r = new Rack(rackPositions[column][row]);
                }
                // If a new Rack is created set the holder to be the RackPosition
                if (r != null) {
                    r.setHolder(rackPositions[column][row]);
                    rackPositions[column][row].setRack(r);
                }
            }
        }
    }

    /**
     * This function fills a given Robot array with created robots on a node
     * @param robots The array of Robots the be filled
     * @param nodes The array of Nodes in the warehouse
     */
    public static void createRobots(Robot[] robots, List<Node> nodes) {
        for (int i = 0; i < robots.length; i++) {
            robots[i] = new Robot(nodes.get(i));
        }
    }


}

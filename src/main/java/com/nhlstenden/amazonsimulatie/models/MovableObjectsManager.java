package com.nhlstenden.amazonsimulatie.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovableObjectsManager {

    private MovableObject[][] racks;
    private MovableObject[] robots;


    public MovableObjectsManager()
    {
        racks = new Rack[4][4];

        double x = 0, z = 0;

        for (int column = 0; column < racks.length; column++)
        {
            x = 0;
            if (column % 2 == 0 && column != 0) z += 2.5;
            else if(column != 0) z += 0.5;


            for (int row = 0; row < racks[column].length; row++)
            {
                if (row % 2 == 0 && row != 0) x += 2.5;
                else if(row != 0) x += 0.5;

                racks[column][row] = new Rack(x, 0.15, z);
            }
        }
    }

    // TODO: Add the truck
    public List<Object3D> getMovableObjectsAsList()
    {
//        List<MovableObject> allObjects = new ArrayList<>(Arrays.asList(robots));
        List<Object3D> allObjects = new ArrayList<Object3D>();

//        for (MovableObject robot : robots)
//        {
//            allObjects.add(new ProxyObject3D((Object3D) robot));
//        }

        for (MovableObject[] r : racks)
        {
            for (MovableObject rack : r)
            {
                allObjects.add(new ProxyObject3D((Object3D) rack));
            }
        }

        return allObjects;
    }


}

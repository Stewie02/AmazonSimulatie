package com.nhlstenden.amazonsimulatie.models.objects;

import com.nhlstenden.amazonsimulatie.models.position.Position;
import com.nhlstenden.amazonsimulatie.models.warehousechanges.WarehouseChange;
import com.nhlstenden.amazonsimulatie.models.objects.interfaces.Object3D;

import java.util.UUID;

/**
 * This abstract class is used in an object that moves.
 * It has all the properties that are used at the client side.
 */
public abstract class MovableObject extends HasPosition implements Object3D {

    private double rotationX, rotationY, rotationZ;
    private final UUID uuid;

    /**
     * This constructor creates a Position object and an UUID
     * @param x The x location of the object
     * @param y The y location of the movable object
     * @param z The z location of the movable object
     */
    public MovableObject(double x, double y, double z) {
        super(x, y, z);

        this.uuid = UUID.randomUUID();
    }

    /**
     * This constructor calls other constructor and gives the values of the given Position
     * @param pos The starting position of the robot
     */
    public MovableObject(Position pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }

    /**
     * Returns the robot UUID
     * @return The UUID of the robot
     */
    public String getUUID() {
        return this.uuid.toString();
    }

    /**
     * In this function happens all the movement of the robot per frame
     * @return The change of the World after the function call
     */
    public abstract WarehouseChange update();

    /**
     * This function return the rotation around the X-axis
     * @return the rotation around the X-axis
     */
    public double getRotationX() {
        return this.rotationX;
    }

    /**
     * This function return the rotation around the Y-axis
     * @return the rotation around the Y-axis
     */
    public double getRotationY() {
        return this.rotationY;
    }

    /**
     * This function return the rotation around the Z-axis
     * @return the rotation around the Z-axis
     */
    public double getRotationZ() {
        return this.rotationZ;
    }

    /**
     * Returns the type of the object
     * @return The object type
     */
    public abstract String getType();

}
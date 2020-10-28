package com.nhlstenden.amazonsimulatie.models.objects.interfaces;

/**
 * An Object3D is an object in the World which has 3 dimensions.
 * This interface contains all the methods which a Object3D should implement
 * These methods are mostly used in the DefaultWebSocketView
 */
public interface Object3D {

    /**
     * Returns the UUID of the object
     * @return The UUID
     */
    String getUUID();

    /**
     * Returns the type of the object
     * @return The type
     */
    String getType();

    /**
     * Returns the X-coordinate of the object
     * @return The X-coordinate
     */
    double getX();

    /**
     * Returns the Y-coordinate of the object
     * @return The Y-coordinate
     */
    double getY();

    /**
     * Returns the Z-coordinate of the object
     * @return The Z-coordinate
     */
    double getZ();

    /**
     * Returns the rotation around the X-axis of the object
     * @return The rotation around the X-axis
     */
    double getRotationX();

    /**
     * Returns the rotation around the X-axis of the object
     * @return The rotation around the X-axis
     */
    double getRotationY();

    /**
     * Returns the rotation around the Z-axis of the object
     * @return The rotation around the Z-axis
     */
    double getRotationZ();
}
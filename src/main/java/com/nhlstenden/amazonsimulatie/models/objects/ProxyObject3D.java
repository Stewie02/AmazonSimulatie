package com.nhlstenden.amazonsimulatie.models.objects;

import com.nhlstenden.amazonsimulatie.models.objects.interfaces.Object3D;

/*
 * This is a proxy class of the Object3D
 * We make sure nobody can this the object with this class!!!
 */
public class ProxyObject3D extends HasPosition implements Object3D {
    private final Object3D object;

    /**
     * Takes in the object and stores it in the object variable
     * @param object the Object3D that is going to be the proxy object
     */
    public ProxyObject3D(Object3D object) {
        super(object.getX(), object.getY(), object.getZ());
        this.object = object;
    }

    /**
     * Returns the UUID of the object
     * @return the UUID
     */
    @Override
    public String getUUID() {
        return this.object.getUUID();
    }

    /**
     * Returns the type of the object
     * @return the type
     */
    @Override
    public String getType() {
        return this.object.getType();
    }

    /**
     * Returns the rotation around the X-axis of the object
     * @return the rotation around the X-axis
     */
    @Override
    public double getRotationX() {
        return this.object.getRotationX();
    }

    /**
     * Returns the rotation around the Y-axis of the object
     * @return the rotation around the Y-axis
     */
    @Override
    public double getRotationY() {
        return this.object.getRotationY();
    }

    /**
     * Returns the rotation around the Z-axis of the object
     * @return the rotation around the Z-axis
     */
    @Override
    public double getRotationZ() {
        return this.object.getRotationZ();
    }


}
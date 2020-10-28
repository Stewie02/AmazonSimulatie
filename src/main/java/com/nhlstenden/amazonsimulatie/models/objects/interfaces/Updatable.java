package com.nhlstenden.amazonsimulatie.models.objects.interfaces;

import com.nhlstenden.amazonsimulatie.models.warehousechanges.WarehouseChange;

/*
 * An updatable object in the warehouse is an object that can change it's own state
 */
public interface Updatable {

    /**
     * When this function is called the Object does something were it state changes
     * It returns an WorldChange, this one will be send to the Controller which then sends it back to clients
     * @return An WorldChange
     */
    WarehouseChange update();
}
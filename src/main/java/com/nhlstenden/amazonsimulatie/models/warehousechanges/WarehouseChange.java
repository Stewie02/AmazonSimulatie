package com.nhlstenden.amazonsimulatie.models.warehousechanges;

/**
 * A WorldChange is holds the information of a change that is happened in the World
 *
 */
public interface WarehouseChange {

    /**
     * The JSON string that will be read by the client
     * @return String in JSON format
     */
    String getParametersString();

    /**
     * The command of what happened
     * @return The command
     */
    String getCommand();

}

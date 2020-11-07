package com.nhlstenden.amazonsimulatie.controllers;

import java.beans.PropertyChangeEvent;

import com.nhlstenden.amazonsimulatie.models.WarehouseManager;
import com.nhlstenden.amazonsimulatie.models.objects.interfaces.Object3D;
import com.nhlstenden.amazonsimulatie.models.warehousechanges.WarehouseChange;
import com.nhlstenden.amazonsimulatie.views.SimulationView;

/**
 * This is the controller that controls the Warehouse simulation.
 * It inherits from the abstract class Controller to give it some extra functionalities
 */
public class SimulationController extends Controller {

    /**
     * Calls the constructor of it's superclass
     * @param warehouseManager The simulation object to run
     */
    public SimulationController(WarehouseManager warehouseManager) {
        super(warehouseManager);
    }

    /**
     * This function is being called when the application starts
     * It's has an infinite loop where it updates the Manager given in the constructor
     */
    @Override
    public void run() {
        while (true) {
            this.warehouseManager.update();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This function is called when a new WebSocketConnection is established
     * @param view The new View
     */
    @Override
    protected void onViewAdded(final SimulationView view) {
        final Controller t = this;

        // This command will be executed when the data can't be sent
        view.onViewClose(() -> t.removeView(view));

        // Sends the RackPositions to the new client
        view.sendRackPositions(this.warehouseManager.getRackPositions());

        // Send the build command to the client with the object
        for (Object3D object : this.warehouseManager.getAllMovableObjects()) {
            view.build(object);
        }
    }

    /**
     * This function is called when some models is changed in the WarehouseManager
     * @param evt The event with the data
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        for(int i = 0; i < this.getViews().size(); i++) {
            SimulationView currentView = this.getViews().get(i);
            if(currentView != null) {
                currentView.sendWorldChange((WarehouseChange) evt.getNewValue());
            }
        }
    }

}
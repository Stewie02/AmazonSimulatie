package com.nhlstenden.amazonsimulatie.controllers;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import com.nhlstenden.amazonsimulatie.models.WarehouseManager;
import com.nhlstenden.amazonsimulatie.views.SimulationView;

/**
 * This abstract controller class has some standard functionality for running a simulation.
 * The controller has the run function which needs the be called, then the simulation will be updated
 */
public abstract class Controller implements Runnable, PropertyChangeListener {

    private final List<SimulationView> views;
    protected final WarehouseManager warehouseManager;

    public Controller(WarehouseManager model) {
        this(model, new ArrayList<>());
    }

    public Controller(WarehouseManager model, List<SimulationView> views) {
        this.warehouseManager = model;
        this.warehouseManager.addObserver(this);
        this.views = new ArrayList<>(views);
    }

    /**
     * First the new view execute the onViewAdded function
     * This is because we can send the initialisation data so the client can build the world
     * After this we'll add the view to the views to send the new data
     * @param view View to add
     */
    public void addView(SimulationView view) {
        this.onViewAdded(view);
        this.views.add(view);
    }

    /**
     * This method is called when a view is added
     * @param view View that is added
     */
    protected abstract void onViewAdded(SimulationView view);

    /**
     * Returns the views list. Be advised that this is the internal list, for use by the controller only.
     * @return The internal list of views.
     */
    protected List<SimulationView> getViews() {
        return this.views;
    }

    protected void removeView(SimulationView view) {
        this.views.remove(view);
    }

    /**
     * Method to start the controller in a new thread.
     */
    public final void start() {
        new Thread(this).start();
    }

    public abstract void run();
}
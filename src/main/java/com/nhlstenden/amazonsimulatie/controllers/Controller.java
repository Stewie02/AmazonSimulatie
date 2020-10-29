package com.nhlstenden.amazonsimulatie.controllers;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import com.nhlstenden.amazonsimulatie.models.WarehouseManager;
import com.nhlstenden.amazonsimulatie.views.SimulationView;

/*
 * Dit is de abstracte controller class. Deze class is abstract omdat het een soort
 * standaard controller is. De class heeft standaardfunctionaliteit voor het managen
 * van een model en een aantal views. Ook kun je de controller aanzetten via de start()
 * methode. Wanneer dit gebeurd wordt een nieuwe thread opgestart waarin de controller
 * de run() methode uitvoerd. Je kunt de controller dus "runnen" binnen een aparte thread.
 * De rest van de functionaliteit, zoals wat de controller precies moet doen in de run()
 * methode, kun je later zelf nog invullen door verder te bouwen op deze class.
 */
public abstract class Controller implements Runnable, PropertyChangeListener {

    private final List<SimulationView> views;
    protected WarehouseManager warehouseManager;

    public Controller(WarehouseManager model) {
        this(model, new ArrayList<>());
    }

    public Controller(WarehouseManager model, List<SimulationView> views) {
        this.warehouseManager = model;
        this.warehouseManager.addObserver(this); //Automatisch wordt deze controller toegevoegd aan het model om updates te ontvangen.
        this.views = new ArrayList<>(views);
    }

    /**
     * First the new view execute the onViewAdded function
     * This is because we can send the initialisation data so the client can build the world
     * After this we'll add the view to the views to send the new data
     * @param view
     */
    public void addView(SimulationView view) {
        this.onViewAdded(view);
        this.views.add(view);
    }

    /*
     * Deze methode kan later geïmplementeerd worden om aan te geven wat er moet
     * gebeuren met een view die nieuw toegevoegd wordt aan de controller.
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
     * Returns the internal model used by the controller.
     * @return The internal model.
     */
    protected WarehouseManager getWorld() {
        return this.warehouseManager;
    }

    /**
     * Method to start the controller in a new thread.
     */
    public final void start() {
        new Thread(this).start();
    }

    public abstract void run();
}
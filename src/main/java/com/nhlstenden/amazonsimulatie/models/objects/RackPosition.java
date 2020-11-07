package com.nhlstenden.amazonsimulatie.models.objects;

import com.nhlstenden.amazonsimulatie.models.objects.interfaces.CanHoldRacks;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Node;

import java.util.UUID;

/**
 * A RackPosition is a place in the warehouse where a Rack can stand
 */
public class RackPosition extends HasPosition implements CanHoldRacks {

    private Rack rack = null;
    private final UUID uuid;
    private Node adjacentNode = null;

    /**
     * Creates the Position of the RackPosition and sets the given Rack
     * @param x The X-coordinate of the RackPosition
     * @param y The Y-coordinate of the RackPosition
     * @param z The Z-coordinate of the RackPosition
     * @param rack The Rack which the RackPosition is holding
     */
    public RackPosition(double x, double y, double z, Rack rack) {
        this(x, y, z);
        this.rack = rack;
    }

    /**
     * Creates the Position of the RackPosition
     * @param x The X-coordinate of the RackPosition
     * @param y The Y-coordinate of the RackPosition
     * @param z The Z-coordinate of the RackPosition
     */
    public RackPosition(double x, double y, double z) {
        super(x, y, z);
        this.uuid = UUID.randomUUID();
    }

    /**
     * Returns the Rack that the RackPosition holds
     * @return The holding Rack
     */
    public Rack getRack() {
        return rack;
    }

    /**
     * Returns the UUID of the RackPosition
     * @return The UUID
     */
    public String getUUID()
    {
        return this.uuid.toString();
    }

    /**
     * Sets the Rack of the RackPosition
     * @param rack Rack that the RackPosition now holds
     */
    public void setRack(Rack rack) {
        this.rack = rack;
    }

    /**
     * Returns the adjacent Node of the RackPosition
     * @return Adjacent Node
     */
    public Node getAdjacentNode() {
        return adjacentNode;
    }

    /**
     * Sets the adjacent Node of the RackPosition
     * @param adjacentNode The adjacent node of the RackPosition
     */
    public void setAdjacentNode(Node adjacentNode) {
        if (this.adjacentNode == null)
            this.adjacentNode = adjacentNode;
    }

}

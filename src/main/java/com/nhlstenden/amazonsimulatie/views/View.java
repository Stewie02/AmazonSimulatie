package com.nhlstenden.amazonsimulatie.views;

import com.nhlstenden.amazonsimulatie.base.Command;
import com.nhlstenden.amazonsimulatie.models.objects.interfaces.Object3D;
import com.nhlstenden.amazonsimulatie.models.warehousechanges.WarehouseChange;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Node;
import com.nhlstenden.amazonsimulatie.models.objects.RackPosition;

import java.util.List;

/*
 * Deze interface is de beschrijving van een view binnen het systeem.
 * Ze de andere classes voor meer uitleg.
 */
public interface View {
    void update(String event, Object3D data);
    void onViewClose(Command command);

    void sendNode(String command, Node nodes);

    void sendRackPositions(List<RackPosition> rackPositions);

    void sendWorldChange(WarehouseChange warehouseChanges);
}
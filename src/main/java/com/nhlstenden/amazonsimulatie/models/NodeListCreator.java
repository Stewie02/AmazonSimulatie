package com.nhlstenden.amazonsimulatie.models;

import com.nhlstenden.amazonsimulatie.models.PathFinding.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NodeListCreator {

    public static List<Node> createNodeList(RackPosition[][] rackPositions) {
        List<Node> nodeList = new ArrayList<>();

        double pathSize = Constants.DISTANCE_BETWEEN_RACK_WITH_PATH - Constants.RACK_POSITION_SIZE;
        double distanceToMiddleOfPath = (Constants.RACK_POSITION_SIZE / 2) + (pathSize / 2);

        BoxPart[][] boxParts = new BoxPart[Constants.AMOUNT_OF_RACKS_WIDTH / 2][Constants.AMOUNT_OF_RACKS_HEIGHT / 2];

        for (int i = 0; i < Constants.AMOUNT_OF_RACKS_HEIGHT / 2; i++)
        {
            for (int j = 0; j < Constants.AMOUNT_OF_RACKS_WIDTH / 2; j++)
            {
                Node[] nodes = new Node[5];
                RackPosition[] rPs = new RackPosition[4];
                rPs[RackPositionConstants.TOP_LEFT] = rackPositions[i * 2][j * 2];
                rPs[RackPositionConstants.TOP_RIGHT] = rackPositions[i * 2][j * 2 + 1];
                rPs[RackPositionConstants.BOTTOM_LEFT] = rackPositions[i * 2 + 1][j * 2];
                rPs[RackPositionConstants.BOTTOM_RIGHT] = rackPositions[i * 2 + 1][j * 2 + 1];

                nodes[NodeConstants.TOP_LEFT] = createNode(rPs[RackPositionConstants.TOP_LEFT], NodeConstants.TOP_LEFT, distanceToMiddleOfPath);
                nodes[NodeConstants.TOP_MIDDLE] = createNode(rPs[RackPositionConstants.TOP_LEFT], NodeConstants.TOP_MIDDLE, distanceToMiddleOfPath);
                nodes[NodeConstants.TOP_RIGHT] = createNode(rPs[RackPositionConstants.TOP_RIGHT], NodeConstants.TOP_RIGHT, distanceToMiddleOfPath);
                nodes[NodeConstants.LEFT_MIDDLE] = createNode(rPs[RackPositionConstants.TOP_LEFT], NodeConstants.LEFT_MIDDLE, distanceToMiddleOfPath);
                nodes[NodeConstants.LEFT_BOTTOM] = createNode(rPs[RackPositionConstants.BOTTOM_LEFT], NodeConstants.LEFT_BOTTOM, distanceToMiddleOfPath);

                connectNodes(nodes[NodeConstants.TOP_LEFT], nodes[NodeConstants.TOP_MIDDLE]);
                connectNodes(nodes[NodeConstants.TOP_MIDDLE], nodes[NodeConstants.TOP_RIGHT]);
                connectNodes(nodes[NodeConstants.TOP_LEFT], nodes[NodeConstants.LEFT_MIDDLE]);
                connectNodes(nodes[NodeConstants.LEFT_MIDDLE], nodes[NodeConstants.LEFT_BOTTOM]);

                boxParts[i][j] = new BoxPart(nodes, rPs);

                nodeList.addAll(Arrays.asList(nodes));
            }
        }

        for (int i = 0; i < boxParts.length -1; i++)
        {
            for (int j = 0; j < boxParts[0].length -1; j++)
            {
                Node n1 = boxParts[i][j].getNode(NodeConstants.TOP_RIGHT);
                Node n2 = boxParts[i][j + 1].getNode(NodeConstants.TOP_LEFT);
                connectNodes(n1, n2);

            }
        }

        for (int i = 0; i < boxParts[0].length -1; i++)
        {
            for (int j = 0; j < boxParts.length -1; j++)
            {
                BoxPart b1 = boxParts[j][i];
                BoxPart b2 = boxParts[j + 1][i];
                Node n1 = b1.getNode(NodeConstants.LEFT_BOTTOM);
                Node n2 = b2.getNode(NodeConstants.TOP_LEFT);
                connectNodes(n1, n2);
            }
        }


        return nodeList;
    }

    private static Node createNode(RackPosition rackPosition, int whichOne, double difference)
    {
        double x, z;

        if (whichOne == NodeConstants.TOP_LEFT) {
            x = rackPosition.getPosition().getX() - difference;
            z = rackPosition.getPosition().getZ() - difference;
        }
        else if (whichOne == NodeConstants.TOP_MIDDLE || whichOne == NodeConstants.TOP_RIGHT) {
            x = rackPosition.getPosition().getX();
            z = rackPosition.getPosition().getZ() - difference;
        }
        else {
            x = rackPosition.getPosition().getX() - difference;
            z = rackPosition.getPosition().getZ();
        }
        return new Node(x, 0.15, z);
    }

    private static void connectNodes(Node n1, Node n2)
    {
        n1.addAdjacent(n2);
        n2.addAdjacent(n1);
    }
}

class BoxPart {

    private final Node[] nodes;
    private final RackPosition[] rackPositions;

    public BoxPart(Node[] nodes, RackPosition[] rackPositions)
    {
        this.nodes = nodes;
        this.rackPositions = rackPositions;
    }

    public Node getNode(int index)
    {
        return nodes[index];
    }

    public RackPosition getRackPosition(int index) {
        return this.rackPositions[index];
    }

}

class NodeConstants {

    public final static int TOP_LEFT = 0;
    public final static int TOP_MIDDLE = 1;
    public final static int TOP_RIGHT = 2;
    public final static int LEFT_MIDDLE = 3;
    public final static int LEFT_BOTTOM = 4;

}

class RackPositionConstants {

    public final static int TOP_LEFT = 0;
    public final static int TOP_RIGHT = 1;
    public final static int BOTTOM_LEFT = 2;
    public final static int BOTTOM_RIGHT = 3;

}
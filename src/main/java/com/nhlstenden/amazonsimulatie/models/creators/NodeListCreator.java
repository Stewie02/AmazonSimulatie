package com.nhlstenden.amazonsimulatie.models.creators;

import com.nhlstenden.amazonsimulatie.models.Constants;
import com.nhlstenden.amazonsimulatie.models.objects.RackPosition;
import com.nhlstenden.amazonsimulatie.models.pathfinding.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NodeListCreator {

    /**
     * This function takes in a multidimensional array of RackPositions and creates nodes for the Dijkstra algorithm
     * It also connects the adjacent nodes to each other
     * @param rackPositions Multidimensional array of the RackPositions that are in the world
     * @return The generated list of the nodes
     */
    public static List<Node> createNodeList(RackPosition[][] rackPositions) {
        List<Node> nodeList = new ArrayList<>();

        // First we calculate how big the distance between nodes should be
        double pathSize = Constants.DISTANCE_BETWEEN_RACK_WITH_PATH - Constants.RACK_POSITION_SIZE;
        double distanceToMiddleOfPath = (Constants.RACK_POSITION_SIZE / 2) + (pathSize / 2);

        // Here we create an multidimensional array of BoxParts
        BoxPart[][] boxParts = new BoxPart[Constants.AMOUNT_OF_RACKS_HEIGHT / 2][Constants.AMOUNT_OF_RACKS_WIDTH / 2];

        // Now we iterate through the multidimensional array
        for (int i = 0; i < Constants.AMOUNT_OF_RACKS_HEIGHT / 2; i++)
        {
            for (int j = 0; j < Constants.AMOUNT_OF_RACKS_WIDTH / 2; j++)
            {
                Node[] nodes = new Node[5];
                RackPosition[] rPs = new RackPosition[4];

                // Here we take the RackPositions from the multidimensional array and put them in the local array
                rPs[RackPositionConstants.TOP_LEFT] = rackPositions[i * 2][j * 2];
                rPs[RackPositionConstants.TOP_RIGHT] = rackPositions[i * 2][j * 2 + 1];
                rPs[RackPositionConstants.BOTTOM_LEFT] = rackPositions[i * 2 + 1][j * 2];
                rPs[RackPositionConstants.BOTTOM_RIGHT] = rackPositions[i * 2 + 1][j * 2 + 1];

                // Here we create the nodes according to the RackPositions
                nodes[NodeConstants.TOP_LEFT] = createNode(rPs[RackPositionConstants.TOP_LEFT], NodeConstants.TOP_LEFT, distanceToMiddleOfPath);
                nodes[NodeConstants.TOP_MIDDLE] = createNode(rPs[RackPositionConstants.TOP_LEFT], NodeConstants.TOP_MIDDLE, distanceToMiddleOfPath);
                nodes[NodeConstants.TOP_RIGHT] = createNode(rPs[RackPositionConstants.TOP_RIGHT], NodeConstants.TOP_RIGHT, distanceToMiddleOfPath);
                nodes[NodeConstants.LEFT_MIDDLE] = createNode(rPs[RackPositionConstants.TOP_LEFT], NodeConstants.LEFT_MIDDLE, distanceToMiddleOfPath);
                nodes[NodeConstants.LEFT_BOTTOM] = createNode(rPs[RackPositionConstants.BOTTOM_LEFT], NodeConstants.LEFT_BOTTOM, distanceToMiddleOfPath);

                // Here we connect the RackPositions to the adjacent nodes
                rPs[RackPositionConstants.TOP_LEFT].setAdjacentNode(nodes[NodeConstants.TOP_MIDDLE]);
                rPs[RackPositionConstants.TOP_RIGHT].setAdjacentNode(nodes[NodeConstants.TOP_RIGHT]);
                if (i > 0) {
                    boxParts[i - 1][j].getRackPosition(RackPositionConstants.BOTTOM_LEFT).setAdjacentNode(nodes[NodeConstants.TOP_MIDDLE]);
                    boxParts[i - 1][j].getRackPosition(RackPositionConstants.BOTTOM_RIGHT).setAdjacentNode(nodes[NodeConstants.TOP_RIGHT]);
                }

                // Here we connect the nodes to each other
                connectNodes(nodes[NodeConstants.TOP_LEFT], nodes[NodeConstants.TOP_MIDDLE]);
                connectNodes(nodes[NodeConstants.TOP_MIDDLE], nodes[NodeConstants.TOP_RIGHT]);
                connectNodes(nodes[NodeConstants.TOP_LEFT], nodes[NodeConstants.LEFT_MIDDLE]);
                connectNodes(nodes[NodeConstants.LEFT_MIDDLE], nodes[NodeConstants.LEFT_BOTTOM]);

                // Put this BoxPart in the array
                boxParts[i][j] = new BoxPart(nodes, rPs);

                // Add the created nodes to the list of nodes
                nodeList.addAll(Arrays.asList(nodes));
            }
        }

        // Connecting the different BoxPart to each other
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

        // Creating the bottom row of nodes
        double distanceBetweenNodes = boxParts[0][1].getNode(0).getX() - boxParts[0][0].getNode(0).getX();
        Node previousNode = null;

        for (BoxPart boxPart : boxParts[boxParts.length - 1]) {
            for (int i = 0; i < 3; i++) {
                Node n = boxPart.getNode(i);
                Node newNode = new Node(n.getX(), n.getY(), n.getZ() + distanceBetweenNodes);

                if (i == 0) connectNodes(boxPart.getNode(NodeConstants.LEFT_BOTTOM), newNode);
                if (i > 0) boxPart.getRackPosition(i+1).setAdjacentNode(newNode);
                if (previousNode != null) connectNodes(newNode, previousNode);

                nodeList.add(newNode);
                previousNode = newNode;
            }
        }

        return nodeList;
    }

    /**
     * This function creates an nodes according to the given parameters
     * @param rackPosition We use the location of the RackPosition to know the location of the node
     * @param whichOne Tells us the position of the node relative to the RackPosition
     * @param difference Distance from the node to the RackPosition
     * @return The created node
     */
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
        return new Node(x, 0, z);
    }

    /**
     * Takes two nodes and connects them together
     * n1 will be connected to n2 and the other way around
     * @param n1 The first node
     * @param n2 The second second
     */
    private static void connectNodes(Node n1, Node n2)
    {
        n1.addAdjacent(n2);
        n2.addAdjacent(n1);
    }
}

/**
 * A BoxPart is an part of the warehouse
 * It consists of 4 RackPosition and 5 Nodes
 */
class BoxPart {

    private final Node[] nodes;
    private final RackPosition[] rackPositions;

    /**
     * Takes the Nodes and RackPositions and assigns them to instance variables
     * @param nodes The Nodes in the BoxPart
     * @param rackPositions The RackPositions in the BoxPart
     */
    public BoxPart(Node[] nodes, RackPosition[] rackPositions)
    {
        this.nodes = nodes;
        this.rackPositions = rackPositions;
    }

    /**
     * This function returns one single Node from the Node array
     * @param index Index of the requested node
     * @return The Node from the array with the given index
     */
    public Node getNode(int index)
    {
        return nodes[index];
    }

    /**
     * This function returns one single RackPosition from the RackPosition array
     * @param index Index of the requested RackPosition
     * @return The RackPosition from the array with the given index
     */
    public RackPosition getRackPosition(int index) {
        return this.rackPositions[index];
    }

}

/**
 * This class contains some static integers to tell the location to a Node in the Node array
 */
class NodeConstants {

    public final static int TOP_LEFT = 0;
    public final static int TOP_MIDDLE = 1;
    public final static int TOP_RIGHT = 2;
    public final static int LEFT_MIDDLE = 3;
    public final static int LEFT_BOTTOM = 4;

}

/**
 * This class contains some static integers to tell the location to a RackPosition in the RackPosition array
 */
class RackPositionConstants {

    public final static int TOP_LEFT = 0;
    public final static int TOP_RIGHT = 1;
    public final static int BOTTOM_LEFT = 2;
    public final static int BOTTOM_RIGHT = 3;

}
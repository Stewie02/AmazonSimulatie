package com.nhlstenden.amazonsimulatie.models.pathfinding;

import com.nhlstenden.amazonsimulatie.models.position.Position;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DijkstraTest {

    /**
     * In this test we create 5 nodes and put them in variables a, b, c, d, e
     * We connect a to b and d to c. We connect them both ways around
     * And a, b, c and d is connected to e and e to all the other nodes
     * Then we try to find the path with dijkstra and if it is equal to the hardcoded path
     * Then the test is completed successfully
     */
    @Test
    public void calculateFastestPath() {

        Node a = new Node(0, 0, 0);
        Node b = new Node(1, 0, 0);
        Node c = new Node(0, 0, 1);
        Node d = new Node(1, 0, 1);
        Node e = new Node(0.5, 0, 0.5);

        connectNodes(a, b);
        connectNodes(d, c);
        connectNodes(a, c);
        connectNodes(a, e);
        connectNodes(b, e);
        connectNodes(c, e);
        connectNodes(d, e);

        Dijkstra dijkstra = new Dijkstra(new ArrayList<>(Arrays.asList(a, b, c, d, e)));

        List<Position> actual = dijkstra.giveShortestPath(a.getPosition(), d);
        List<Position> expected = new ArrayList<>(Arrays.asList(e.getPosition(), d.getPosition()));

        int min = Math.min(actual.size(), expected.size());
        boolean same = actual.size() == expected.size();

        for (int i = 0; i < min; i++) {
            if (!samePos(actual.get(i), expected.get(i))) {
                same = false;
                break;
            }
        }
        assertSame(same, true);
    }

    private boolean samePos(Position pos1, Position pos2) {
        return pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY() && pos1.getZ() == pos2.getZ();
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
package org.application.IA.IA_NonPiuSoli;

import org.application.IA.IA_NonPiuSoli.Node;
import org.application.model.Block;

import java.util.*;

public class AStar {

    private static final int[][] DIRECTIONS = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    public static List<Node> findPath(Block[][] grid, Node start, Node goal, HashSet<Node> closed ) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        HashSet<Node> closedList = new HashSet<>();

        closedList = closed;

        start.gCost = 0;
        start.hCost = heuristic(start, goal);
        openList.add(start);

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.equals(goal)) {
                return reconstructPath(current);
            }

            closedList.add(current);

            for (int[] direction : DIRECTIONS) {
                int newX = current.x + direction[0];
                int newY = current.y + direction[1];

                if (isValid(grid, newX, newY)) {
                    Node neighbor = new Node(newX, newY);

                    if (closedList.contains(neighbor)) {
                        continue;
                    }

                    int tentativeGCost = current.gCost + 1;

                    if (!openList.contains(neighbor) || tentativeGCost < neighbor.gCost) {
                        neighbor.gCost = tentativeGCost;
                        neighbor.hCost = heuristic(neighbor, goal);
                        neighbor.parent = current;

                        if (!openList.contains(neighbor)) {
                            openList.add(neighbor);
                        }
                    }
                }
            }
        }

        return Collections.emptyList(); // Nessun percorso trovato
    }


    private static int heuristic(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private static boolean isValid(Block[][] grid, int x, int y) {
        return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && grid[x][y].type() == 0;
    }

    private static List<Node> reconstructPath(Node goal) {
        List<Node> path = new ArrayList<>();
        for (Node node = goal; node != null; node = node.parent) {
            path.add(node);
        }
        Collections.reverse(path);
        return path;
    }

}

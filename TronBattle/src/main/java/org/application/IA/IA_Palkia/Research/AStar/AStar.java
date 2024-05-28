package org.application.IA.IA_Palkia.Research.AStar;

import org.application.IA.IA_Palkia.Research.Support.Cell;
import org.application.model.Block;

import java.util.*;

public class AStar {

    private static int Heuristic(Cell start, Cell end) {
        return (int) Math.sqrt(Math.pow(start.x - end.x, 2) + Math.pow(start.y - end.y, 2));
    }

    public static int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // Destra, sinistra, giù, su

    public static List<Cell> findPath(Block[][] matrix, Cell start, Cell end, int maxDepth) {
        Set<Integer> avoidTypes = new HashSet<>();
        for (int i = 5; i <= 8; i++) {
            avoidTypes.add(i);
        }
        int n = matrix.length; // Righe corrispondono alla dimensione esterna dell'array
        int m = matrix[0].length; // Colonne corrispondono alla dimensione interna dell'array

        HashMap<String, Cell> parent = new HashMap<>();
        parent.put(start.x + "-" + start.y, null);
        int costoPercorso = 0;
        Queue<Cell> queue = new PriorityQueue<>(Comparator.comparingInt(a -> Heuristic(a, end) + costoPercorso));
        queue.add(start);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            if(current.c == maxDepth) {
                return null;
            }
            for (int[] dir : directions) {
                int x = current.x + dir[0];
                int y = current.y + dir[1];
                Cell next = new Cell(x, y, current.c + 1);
                if (x >= 0 && x < n && y >= 0 && y < m && !avoidTypes.contains(matrix[x][y].type())) {
                    if ((!parent.containsKey(next.x + "-" + next.y) || (parent.get(next.x + "-" + next.y) != null && parent.get(next.x + "-" + next.y).c > next.c))) {
                        parent.put(next.x + "-" + next.y, current);
                        queue.add(next);
                        if (next.x == end.x && next.y == end.y) {
                            return buildPath(parent, next);
                        }
                    }
                }
            }
        }
        return null;
    }

    private static List<Cell> buildPath(HashMap<String, Cell> parent, Cell end) {
        List<Cell> path = new ArrayList<>();
        Cell current = end;
        while (current != null) {
            path.add(current);
            current = parent.get(current.x + "-" + current.y);
        }
        Collections.reverse(path);
        return path;
    }
}

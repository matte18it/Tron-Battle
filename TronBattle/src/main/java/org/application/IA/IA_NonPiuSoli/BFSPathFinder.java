package org.application.IA.IA_NonPiuSoli;

import org.application.model.Block;
import java.util.LinkedList;
import java.util.Queue;

public class BFSPathFinder {

    static class Node {
        int x, y, distance;

        Node(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }
    }

    public static int bfs(Block[][] matrix, int startX, int startY, int playerHead) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};

        Queue<Node> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];

        queue.add(new Node(startX, startY, 0));
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int x = current.x;
            int y = current.y;
            int distance = current.distance;

            //Esplora vicini
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx >= 0 && nx < rows && ny >= 0 && ny < cols && !visited[nx][ny]) {
                    if (matrix[nx][ny].type() == Block.EMPTY) {
                        visited[nx][ny] = true;
                        queue.add(new Node(nx, ny, distance + 1));
                    } else if (matrix[nx][ny].type() >= Block.PLAYER1_HEAD && matrix[nx][ny].type() <= Block.PLAYER4_HEAD && matrix[nx][ny].type() != playerHead) {
                        return distance + 1;
                    }
                }
            }
        }
        return -1;
    }
}
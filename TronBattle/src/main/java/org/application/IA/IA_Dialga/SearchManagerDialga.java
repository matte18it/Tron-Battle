package org.application.IA.IA_Dialga;

import org.application.model.Block;

import java.util.*;

public class SearchManagerDialga {

    private static SearchManagerDialga instance = null;

    public static SearchManagerDialga getInstance() {
        if (instance == null) {
            instance = new SearchManagerDialga();
        }
        return instance;
    }

    List<CellUtilDialga> findReachableCells(int startX, int startY, Block[][] varBlocks) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        int col = varBlocks.length;
        int row = varBlocks[0].length;
        boolean[][] visited = new boolean[col][row];
        List<CellUtilDialga> reachableCells = new ArrayList<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY, 0});
        visited[startX][startY] = true;
        reachableCells.add(new CellUtilDialga(startX, startY, varBlocks[startX][startY].type(), 0));

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int distance = current[2];

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (isValidCell(nx, ny, col, row, visited)) {
                    int cellType = varBlocks[nx][ny].type();
                    reachableCells.add(new CellUtilDialga(nx, ny, cellType, distance + 1));
                    visited[nx][ny] = true;
                    if (cellType == 0) {
                        queue.add(new int[]{nx, ny, distance + 1});
                    }
                }
            }
        }
        return reachableCells;
    }

    public Integer getDistanceFromEnemy(int startX, int startY, Block[][] varBlocks, int player) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        int col = varBlocks.length;
        int row = varBlocks[0].length;
        boolean[][] visited = new boolean[col][row];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY, 0});
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int distance = current[2];

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (isValidCell(nx, ny, col, row, visited)) {
                    int cellType = varBlocks[nx][ny].type();
                    visited[nx][ny] = true;

                    if (isEnemy(cellType, player)) {
                        return distance + 1;
                    }

                    if (cellType == 0) {
                        queue.add(new int[]{nx, ny, distance + 1});
                    }
                }
            }
        }
        return 0;
    }

    public List<CellUtilDialga> findPath(int startX, int startY, int destX, int destY, Block[][] varBlocks) {
        if (startX == destX && startY == destY) {
            List<CellUtilDialga> path = new ArrayList<>();
            path.add(new CellUtilDialga(startX, startY));
            return path;
        }

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        int col = varBlocks.length;
        int row = varBlocks[0].length;
        boolean[][] visited = new boolean[col][row];
        int[][][] parent = new int[col][row][2];
        initializeParent(parent);

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (isValidCell(nx, ny, col, row, visited) && varBlocks[nx][ny].type() == 0) {
                    queue.add(new int[]{nx, ny});
                    visited[nx][ny] = true;
                    parent[nx][ny][0] = x;
                    parent[nx][ny][1] = y;

                    if (nx == destX && ny == destY) {
                        return reconstructPath( destX, destY, parent);
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    public CellUtilDialga findFirstEnemyCell(int startX, int startY, Block[][] varBlocks, int player) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        int col = varBlocks.length;
        int row = varBlocks[0].length;
        boolean[][] visited = new boolean[col][row];

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY, 0});
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int distance = current[2];

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (isValidCell(nx, ny, col, row, visited)) {
                    int cellType = varBlocks[nx][ny].type();
                    visited[nx][ny] = true;

                    if (isEnemy(cellType, player)) {
                        return new CellUtilDialga(nx, ny, cellType, distance + 1);
                    }

                    queue.add(new int[]{nx, ny, distance + 1});
                }
            }
        }
        return new CellUtilDialga(-1, -1, -1, -1);
    }

    public Integer getEnemiesAround(List<CellUtilDialga> reachables) {
        int enemyCount = 0;
        int player = MainClassDialga.getInstance().getPlayer();

        for (CellUtilDialga cell : reachables) {
            if (isEnemy(cell.getBlockType(), player)) {
                enemyCount++;
            }
        }
        return enemyCount;
    }

    private boolean isValidCell(int x, int y, int col, int row, boolean[][] visited) {
        return x >= 0 && y >= 0 && x < col && y < row && !visited[x][y];
    }

    private boolean isEnemy(int cellType, int player) {
        return cellType != player && cellType > 0 && cellType < 5;
    }

    private void initializeParent(int[][][] parent) {
        for (int[][] ints : parent) {
            for (int[] anInt : ints) {
                Arrays.fill(anInt, -1);
            }
        }
    }

    private List<CellUtilDialga> reconstructPath( int destX, int destY, int[][][] parent) {
        List<CellUtilDialga> path = new ArrayList<>();
        int x = destX;
        int y = destY;

        while (x != -1 && y != -1) {
            path.add(new CellUtilDialga(x, y));
            int px = parent[x][y][0];
            int py = parent[x][y][1];
            x = px;
            y = py;
        }

        Collections.reverse(path);


        return path;
    }
}


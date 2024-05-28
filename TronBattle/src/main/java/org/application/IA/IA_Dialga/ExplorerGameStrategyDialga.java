package org.application.IA.IA_Dialga;

import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.SymbolicConstant;
import org.application.IA.IA_Dialga.asp_classes.explorer.ZonaDialga;
import org.application.IA.IA_Dialga.asp_classes.explorer.ZonaSceltaDialga;
import org.application.model.Block;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ExplorerGameStrategyDialga extends StrategyDialga {

    InputProgram program = new ASPInputProgram();
    Map<ZoneENUMDialga, List<CellUtilDialga>> zones = new HashMap<>();
    Map<ZoneENUMDialga, Integer> enemiesPerZone = new HashMap<>();
    Map<ZoneENUMDialga, List<CellUtilDialga>> reachableCellPerZone = new HashMap<>();
    Map<ZoneENUMDialga, List<CellUtilDialga>> pathPerZone = new HashMap<>();
    Map<ZoneENUMDialga, Integer> distances = new HashMap<>();
    List<CellUtilDialga> path = new ArrayList<>();
    ZoneENUMDialga selectedZone;
    StrategyManagerDialga manager;

    ExplorerGameStrategyDialga(StrategyManagerDialga manager) {
        this.manager = manager;
        program.addFilesPath("encodings/encodings_Dialga/ia-dialga-explorer.asp");
        MainClassDialga.getInstance().addProgram(program);
    }

    @Override
    public int getDirection() {
        if (path.isEmpty() || pathNotValid(path)) {
            generateStrategy();
            getZonaScelta();
            generatePath();
            if (!path.isEmpty())
                path.remove(0);
        }
        CellUtilDialga nextCell = path.get(0);
        System.out.println("Next cell: " + nextCell.getX() + " " + nextCell.getY());
        path.remove(0);
        return findDirection(nextCell.getX(), nextCell.getY());
    }

    private void generatePath() {
        path.clear();
        int startX = MainClassDialga.getInstance().getPlayerX();
        int startY = MainClassDialga.getInstance().getPlayerY();
        CellUtilDialga endCell = findEndCell(this.selectedZone);
        path = SearchManagerDialga.getInstance().findPath(startX, startY, endCell.getX(), endCell.getY(), MainClassDialga.getInstance().getBlocks());
    }

    private CellUtilDialga findEndCell(ZoneENUMDialga zone) {
        List<CellUtilDialga> possibleEndCells = reachableCellPerZone.get(zone);
        // Ordina le celle in base a cell[3]
        possibleEndCells.sort(Comparator.comparingInt(cell -> cell.getDistance()));
        // Trova la cella piu lontana
        return possibleEndCells.get(possibleEndCells.size() - 1);
    }

    private void getZonaScelta() {
        System.out.println(program.getPrograms());
        AnswerSet answerSets = MainClassDialga.getInstance().runASP();
        try {
            for (Object obj : answerSets.getAtoms()) {
                if (obj instanceof ZonaSceltaDialga var) {
                    selectedZone = ZoneENUMDialga.valueOf(var.getNome().toString().toUpperCase());
                    System.out.println("Zona scelta: " + selectedZone);
                }
            }
        } catch (InstantiationException | NoSuchMethodException |
                 IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateStrategy() {
        program.clearPrograms();
        try {
            generateZones();
            findDistanceAndEnemies();

            for (ZoneENUMDialga zone : reachableCellPerZone.keySet()) {
                program.addObjectInput(new ZonaDialga(new SymbolicConstant(zone.toString().toLowerCase()), enemiesPerZone.get(zone), distances.get(zone), pathPerZone.get(zone).size(), zones.get(zone).size()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findDistanceAndEnemies() {
        enemiesPerZone.clear();
        distances.clear();
        for (ZoneENUMDialga zone : zones.keySet()) {
            int enemies = 0;
            int minDistance = Integer.MAX_VALUE;
            for (CellUtilDialga cell : zones.get(zone)) {
                int x = cell.getX();
                int y = cell.getY();
                int type = cell.getBlockType();
                int distance = cell.getDistance();
                if (distance < minDistance) {
                    minDistance = distance;
                }
                if (type > 0 && type < 5 && type != MainClassDialga.getInstance().getPlayer()) {
                    enemies++;
                }
            }
            enemiesPerZone.put(zone, enemies);
            distances.put(zone, minDistance);
        }
    }

    private void generateZones() {
        zones.clear();
        reachableCellPerZone.clear();
        // Inizializza le liste per ogni zona
        for (ZoneENUMDialga zone : ZoneENUMDialga.values()) {
            zones.put(zone, new ArrayList<>());
            reachableCellPerZone.put(zone, new ArrayList<>());
        }

        int playerX = MainClassDialga.getInstance().getPlayerX();
        int playerY = MainClassDialga.getInstance().getPlayerY();
        List<CellUtilDialga> reachableCells = manager.getReachableCells();

        int midX = 15;
        int midY = 13;

        for (CellUtilDialga cell : reachableCells) {
            int x = cell.getX();
            int y = cell.getY();
            int type = cell.getBlockType();

            // Logica per dividere le zone in base alla posizione del giocatore
            if (x > playerX && y < playerY) { // Nord-Est
                if (x <= playerX + midX && y >= playerY  - midY) {
                    zones.get(ZoneENUMDialga.NORTH_EAST_ZONE).add(cell);
                    if (type == 0) {
                        reachableCellPerZone.get(ZoneENUMDialga.NORTH_EAST_ZONE).add(cell);
                    }
                }
            } else if (x > playerX && y > playerY) { // Sud-Est
                if (x <= playerX  + midX && y <= playerY  + midY) {
                    zones.get(ZoneENUMDialga.SOUTH_EAST_ZONE).add(cell);
                    if (type == 0) {
                        reachableCellPerZone.get(ZoneENUMDialga.SOUTH_EAST_ZONE).add(cell);
                    }
                }
            } else if (x < playerX && y < playerY) { // Nord-Ovest
                if (x >= playerX  - midX && y >= playerY  - midY) {
                    zones.get(ZoneENUMDialga.NORTH_WEST_ZONE).add(cell);
                    if (type == 0) {
                        reachableCellPerZone.get(ZoneENUMDialga.NORTH_WEST_ZONE).add(cell);
                    }
                }
            } else if (x < playerX && y > playerY) { // Sud-Ovest
                if (x >= playerX  - midX && y <= playerY  + midY) {
                    zones.get(ZoneENUMDialga.SOUTH_WEST_ZONE).add(cell);
                    if (type == 0) {
                        reachableCellPerZone.get(ZoneENUMDialga.SOUTH_WEST_ZONE).add(cell);
                    }
                }
            }
        }

        // Rimuovi zone vuote
        zones.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        reachableCellPerZone.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        generateReachableCells();
    }

    private void generateReachableCells() {
        pathPerZone.clear();
        for (ZoneENUMDialga zone : reachableCellPerZone.keySet()) {
            pathPerZone.put(zone, new ArrayList<>());
            Block[][] blocks = MainClassDialga.getInstance().getCloneBlocks();
            CellUtilDialga cell = findEndCell(zone);
            List<CellUtilDialga> path = SearchManagerDialga.getInstance().findPath(
                    MainClassDialga.getInstance().getPlayerX(),
                    MainClassDialga.getInstance().getPlayerY(),
                    cell.getX(), cell.getY(), blocks);
            updateBlocksAlongPath(blocks, path);
            List<CellUtilDialga> reachableCells = SearchManagerDialga.getInstance().findReachableCells(cell.getX(),cell.getY(),blocks);
            pathPerZone.put(zone, reachableCells);
        }
    }

    private boolean isInSameZone(int x, int y, List<int[]> cells) {
        for (int[] cell : cells) {
            if (cell[0] == x && cell[1] == y) {
                return true;
            }
        }
        return false;
    }

}

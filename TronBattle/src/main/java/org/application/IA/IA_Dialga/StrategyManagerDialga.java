package org.application.IA.IA_Dialga;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class StrategyManagerDialga {
    private StrategyENUMDialga currentStrategyENUM;
    private StrategyDialga currentStrategy;

    @Getter
    private List<CellUtilDialga> reachableCells;
    @Getter
    private List<CellUtilDialga> reachableCellsEnemy;

    public int getDirection() {
        chooseStrategy();
        return currentStrategy.getDirection();
    }

    private void chooseStrategy() {
        reachableCells = SearchManagerDialga.getInstance().findReachableCells(MainClassDialga.getInstance().getPlayerX(),
                MainClassDialga.getInstance().getPlayerY(),
                MainClassDialga.getInstance().getBlocks());

        if (isClosed(reachableCells)) {
            List<CellUtilDialga> emptyReachableCells = getEmptyReachableCells(reachableCells);
            if (emptyReachableCells.size() > 90) {
                if (currentStrategyENUM != StrategyENUMDialga.DEFENSE) {
                    currentStrategyENUM = StrategyENUMDialga.DEFENSE;
                    currentStrategy = new DefenseStrategyDialga();
                }
            } else {
                if (currentStrategyENUM != StrategyENUMDialga.END_GAME) {
                    currentStrategyENUM = StrategyENUMDialga.END_GAME;
                    currentStrategy = new EndGameStrategyDialga(this);
                }
            }
        } else if (enemyInCentralZone(reachableCells)) {
            if (currentStrategyENUM != StrategyENUMDialga.ATTACK) {
                loadReachableCellsEnemy();
                currentStrategyENUM = StrategyENUMDialga.ATTACK;
                currentStrategy = new AttackGameStrategyDialga(this);
            }
        } else if (aLotOfFarEmptyCell(reachableCells)) {
            if (currentStrategyENUM != StrategyENUMDialga.EXPLORER) {
                currentStrategyENUM = StrategyENUMDialga.EXPLORER;
                currentStrategy = new ExplorerGameStrategyDialga(this);
            }
        } else {
            if (currentStrategyENUM != StrategyENUMDialga.DEFENSE) {
                currentStrategyENUM = StrategyENUMDialga.DEFENSE;
                currentStrategy = new DefenseStrategyDialga();
            }
        }
    }

    private void loadReachableCellsEnemy() {
        CellUtilDialga enemyCell = SearchManagerDialga.getInstance().findFirstEnemyCell(MainClassDialga.getInstance().getPlayerX(),
                MainClassDialga.getInstance().getPlayerY(),
                MainClassDialga.getInstance().getBlocks(), MainClassDialga.getInstance().getPlayer());
        reachableCellsEnemy = SearchManagerDialga.getInstance().findReachableCells(enemyCell.getX(), enemyCell.getY(), MainClassDialga.getInstance().getBlocks());
    }

    private boolean aLotOfFarEmptyCell(List<CellUtilDialga> reachableCells) {
        int count = 0;
        for (CellUtilDialga cell : reachableCells) {
            if (cell.getBlockType() == 0 && cell.getDistance() >= 15) {
                count++;
            }
        }
        return count > 40;
    }

    private boolean enemyInCentralZone(List<CellUtilDialga> reachableCells) {
        int maxDistance = 16;
        for (CellUtilDialga cell : reachableCells) {
            if (cell.getBlockType() != MainClassDialga.getInstance().getPlayer() && cell.getBlockType() > 0 && cell.getBlockType() < 5 && cell.getDistance() <= maxDistance) {
                return true;
            }
        }
        return false;
    }

    private List<CellUtilDialga> getEmptyReachableCells(List<CellUtilDialga> reachableCells) {
        reachableCells.removeIf(cell -> cell.getBlockType() != 0);
        return reachableCells;
    }

    private boolean isClosed(List<CellUtilDialga> reachableCells) {
        for (CellUtilDialga cell : reachableCells) {
            if (cell.getBlockType() != MainClassDialga.getInstance().getPlayer() && cell.getBlockType() > 0 && cell.getBlockType() < 5) {
                return false;
            }
        }
        return true;
    }

    public List<CellUtilDialga> getCelleVicinoAlNemico() {
        List<CellUtilDialga> val = new ArrayList<>();
        int maxDistance = 16;
        for(CellUtilDialga cell : reachableCells){
            if(cell.getDistance() <= maxDistance && cell.getBlockType() == 0){
                val.add(cell);
            }
        }


        return val;
    }

}

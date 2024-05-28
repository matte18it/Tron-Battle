package org.application.IA.IA_Dialga;

import org.application.model.Block;
import org.application.utility.Settings;

import java.util.List;

public abstract class StrategyDialga {

    abstract int getDirection();

    protected int findDirection(int x1, int y1) {
        if (x1 > MainClassDialga.getInstance().getPlayerX())
            return Settings.RIGHT;
        if (x1 < MainClassDialga.getInstance().getPlayerX())
            return Settings.LEFT;
        if (y1 > MainClassDialga.getInstance().getPlayerY())
            return Settings.DOWN;
        if (y1 < MainClassDialga.getInstance().getPlayerY())
            return Settings.UP;
        return 0;
    }

    protected boolean pathNotValid(List<CellUtilDialga> path) {
        for (CellUtilDialga cell : path) {
            if (!MainClassDialga.getInstance().isValidMove(MainClassDialga.getInstance().getBlocks(), cell.getX(), cell.getY())) {
                return true;
            }
        }
        return false;
    }

    protected Block[][] updateBlocksAlongPath(Block[][] blocks, List<CellUtilDialga> path) {
        // Metodo di supporto per aggiornare la matrice di blocchi lungo il percorso
        for (int i = 0; i < path.size(); i++) {
            int x = path.get(i).getX();
            int y = path.get(i).getY();
            if (i == path.size() - 1) {
                blocks[x][y] = new Block(MainClassDialga.getInstance().getPlayer());
            } else {
                blocks[x][y] = new Block(MainClassDialga.getInstance().getPlayerBody());
            }
        }
        return blocks;
    }

}

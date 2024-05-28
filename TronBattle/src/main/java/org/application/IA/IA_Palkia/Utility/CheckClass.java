package org.application.IA.IA_Palkia.Utility;

import org.application.IA.IA_Palkia.Research.BFS.BreadthFirstSearch;
import org.application.IA.IA_Palkia.Research.Support.Cell;
import org.application.IA.IA_Palkia.Research.Support.PlayerPositionType;
import org.application.IA.IA_Palkia.Research.Support.PosizioniPlayers;
import org.application.model.Block;

import java.util.ArrayList;
import java.util.List;

public class CheckClass {
    public static boolean isPlayerEnclosed(Block[][] blocks, int playerHead) {
        //vede le indirect cells di tutti i players e le salva in una lista
        List<Cell> indirectCellsPlayers = new ArrayList<>();
        int mieCelle = 0;
        for(PlayerPositionType player : PosizioniPlayers.posizioniPlayers(blocks)){
            if(player.getId() == playerHead)
                mieCelle = BreadthFirstSearch.getInstance().indirectCell(blocks, new Cell(player.getX(), player.getY(), 0));
            else
                indirectCellsPlayers.add(new Cell(player.getX(), player.getY(), BreadthFirstSearch.getInstance().indirectCell(blocks, new Cell(player.getX(), player.getY(), 0))));
        }

        //controlla se le mie celle sono in numero diverso da quelle di tutti gli altri player, se si, sono chiuso
        int count = 0;
        for(Cell cell : indirectCellsPlayers){
            if(mieCelle < cell.c)
                count++;
        }

        if(count == indirectCellsPlayers.size())
            return true;

        return false;
    }   // controlla se il player è chiuso
    public static boolean isRouteValid(Block[][] blocks, int playerHead) {
        //controllo se ci sta un nemico vicino
        if(BreadthFirstSearch.getInstance().thisIsEnemy(blocks, new Cell(DataClass.getInstance().getPlayerX(), DataClass.getInstance().getPlayerY(), 0), DataClass.getInstance().getEyeVision(), playerHead))
            return false;

        // controlla se le celle rianenti nel cammino sono vuote
        for(Cell cell : DataClass.getInstance().getMinStreet()){
            if(blocks[cell.x][cell.y].type() != 0)
                return false;
        }

        return true;
    }   // controlla se il cammino è valido
    public static boolean isAdjacent(Cell point){
        // controlla se la cella è adiacente al player
        return (point.x == DataClass.getInstance().getPlayerX() && (point.y == DataClass.getInstance().getPlayerY() + 1 || point.y == DataClass.getInstance().getPlayerY() - 1)) || (point.y == DataClass.getInstance().getPlayerY() && (point.x == DataClass.getInstance().getPlayerX() + 1 || point.x == DataClass.getInstance().getPlayerX() - 1));
    }   // controlla se la cella è adiacente al player
    public static int wallNumber(Block[][] blocks) {
        // conto il numero di muri sulla mappa
        int wallNumber = 0;
        for (Block[] block : blocks)
            for (Block value : block)
                if (value.type() == Block.PLAYER1_BODY || value.type() == Block.PLAYER2_BODY || value.type() == Block.PLAYER3_BODY || value.type() == Block.PLAYER4_BODY)
                    wallNumber++;

        return wallNumber;
    }   // conto il numero di muri sulla mappa
}

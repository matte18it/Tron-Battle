package org.application.IA.IA_Palkia.Research.Support;

import org.application.model.Block;

import java.util.ArrayList;

public class PosizioniPlayers {
    public static ArrayList<PlayerPositionType> posizioniPlayers (Block[][] blocks){
        ArrayList<PlayerPositionType> posizioni = new ArrayList<>();
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j].type() == Block.PLAYER1_HEAD || blocks[i][j].type() == Block.PLAYER2_HEAD || blocks[i][j].type() == Block.PLAYER3_HEAD || blocks[i][j].type() == Block.PLAYER4_HEAD)
                    posizioni.add(new PlayerPositionType(i, j, blocks[i][j].type()));
            }
        }
        return posizioni;
    }
}

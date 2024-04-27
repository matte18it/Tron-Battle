package org.application.model;

import org.application.utility.Settings;

public class Game {
    private Block[][] blocks = new Block[Settings.WORLD_SIZEX][Settings.WORLD_SIZEY];
    private int x;
    private int y;
    private int modalitàCorrente;
    private int directionPlayer1;
    private int directionPlayer2;
    private int directionPlayer3;
    private int directionPlayer4;

    public int getDirectionPlayer1() {
        return directionPlayer1;
    }

    public void setDirectionPlayer1(int directionPlayer1) {
        this.directionPlayer1 = directionPlayer1;
    }

    public int getDirectionPlayer3() {
        return directionPlayer3;
    }

    public void setDirectionPlayer3(int directionPlayer3) {
        this.directionPlayer3 = directionPlayer3;
    }

    public int getDirectionPlayer4() {
        return directionPlayer4;
    }

    public void setDirectionPlayer4(int directionPlayer4) {
        this.directionPlayer4 = directionPlayer4;
    }

    public int getDirectionPlayer2() {
        return directionPlayer2;
    }

    public void setDirectionPlayer2(int directionPlayer2) {
        this.directionPlayer2 = directionPlayer2;
    }

    private Game(){

    };
    private static Game instance;
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }


    public void move() {
    }
    public void setModalitaCorrente(int modalitàCorrente) {
        this.modalitàCorrente = modalitàCorrente;
        this.loadWorld();
    }

    private void loadWorld() {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                blocks[i][j] = new Block(Block.EMPTY);
            }
        }
        if(modalitàCorrente==Settings.COMPETITION){
            // Imposta il giocatore 1 nell'angolo in alto a sinistra
            blocks[1][1] = new Block(Block.PLAYER1_HEAD);
            directionPlayer1 = Settings.RIGHT;

            // Imposta il giocatore 2 nell'angolo in alto a destra
            blocks[1][blocks[0].length - 2] = new Block(Block.PLAYER2_HEAD);
            directionPlayer2 = Settings.LEFT;

            // Imposta il giocatore 3 nell'angolo in basso a sinistra
            blocks[blocks.length - 2][1] = new Block(Block.PLAYER3_HEAD);
            directionPlayer3 = Settings.RIGHT;

            // Imposta il giocatore 4 nell'angolo in basso a destra
            blocks[blocks.length - 2][blocks[1].length - 2] = new Block(Block.PLAYER4_HEAD);
            directionPlayer4 = Settings.LEFT;

        }
    }

    public int getModalitàCorrente() {
        return modalitàCorrente;
    }

    public Block[][] getBlocks() {
        return blocks;
    }
}

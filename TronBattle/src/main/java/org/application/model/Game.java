package org.application.model;

import org.application.utility.Settings;

public class Game {
    private Block[][] blocks = new Block[Settings.WORLD_SIZEX][Settings.WORLD_SIZEY];
    private int x;
    private int y;
    private int modalitàCorrente;
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
    public void setModalitàCorrente(int modalitàCorrente) {
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
            blocks[1][1] = new Block(Block.PLAYER1);

            // Imposta il giocatore 2 nell'angolo in alto a destra
            blocks[1][blocks[0].length - 2] = new Block(Block.PLAYER2);

            // Imposta il giocatore 3 nell'angolo in basso a sinistra
            blocks[blocks.length - 2][1] = new Block(Block.PLAYER3);

            // Imposta il giocatore 4 nell'angolo in basso a destra
            blocks[blocks.length - 2][blocks[1].length - 1] = new Block(Block.PLAYER4);

        }
    }

    public int getModalitàCorrente() {
        return modalitàCorrente;
    }

    public Block[][] getBlocks() {
        return blocks;
    }
}

package org.application.model;

import org.application.utility.Settings;

import java.util.Random;

public class Game {
    private final Random random = new Random();
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

    private Game() {

    }

    ;
    private static Game instance;

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }


    public void move() {
        switch (modalitàCorrente) {
            case Settings.SINGLE_PLAYER -> {
                //TODO
            }
            case Settings.TWO_PLAYER -> {
                //TODO
            }
            case Settings.COMPETITION -> {
                directionPlayer1 = 0;
                directionPlayer2 = 2;
                directionPlayer3 = 1;
                directionPlayer4 = 2;
                movePlayer(directionPlayer1, Block.PLAYER1_HEAD, Block.PLAYER1_BODY);
                movePlayer(directionPlayer2, Block.PLAYER2_HEAD, Block.PLAYER2_BODY);
                movePlayer( directionPlayer3, Block.PLAYER3_HEAD, Block.PLAYER3_BODY);
                movePlayer( directionPlayer4, Block.PLAYER4_HEAD, Block.PLAYER4_BODY);
            }
        }
    }

    private void movePlayer( int direction, int headType, int bodyType) {
        // Ottieni la posizione corrente del giocatore
        int[] playerPosition = getPlayerPosition(headType);
        int x = playerPosition[0];
        int y = playerPosition[1];

        // Calcola la nuova posizione del giocatore
        int newX = x;
        int newY = y;
        switch (direction) {
            case Settings.RIGHT:
                newX++;
                break;
            case Settings.LEFT:
                newX--;
                break;
            case Settings.UP:
                newY--;
                break;
            case Settings.DOWN:
                newY++;
                break;
            default:
                // Direzione non valida
                return;


        }
        if(newX < 0 || newX >= blocks.length || newY < 0 || newY >= blocks[0].length || blocks[newX][newY].type() != Block.EMPTY || isCollisionWithOtherPlayer(newX, newY, headType)){
            uccidiGiocatore(headType, bodyType);
            return;}
        blocks[x][y] = new Block(bodyType);
        blocks[newX][newY] = new Block(headType);
    }



    private void uccidiGiocatore(int headType, int bodyType) {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j].type() == headType || blocks[i][j].type() == bodyType) {
                    blocks[i][j] = new Block(Block.EMPTY);
                }
            }
        }
    }

    public void setModalitaCorrente ( int modalitàCorrente){
            this.modalitàCorrente = modalitàCorrente;
            this.loadWorld();
        }

        private void loadWorld () {
            for (int i = 0; i < blocks.length; i++) {
                for (int j = 0; j < blocks[i].length; j++) {
                    blocks[i][j] = new Block(Block.EMPTY);
                }
            }
            if (modalitàCorrente == Settings.COMPETITION) {
                // Imposta il giocatore 1 nell'angolo in alto a sinistra
                blocks[1][1] = new Block(Block.PLAYER1_HEAD);
                directionPlayer1 = Settings.RIGHT;

                // Imposta il giocatore 2 nell'angolo in alto a destra
                blocks[1][6] = new Block(Block.PLAYER2_HEAD);
                directionPlayer2 = Settings.LEFT;

                // Imposta il giocatore 3 nell'angolo in basso a sinistra
                blocks[blocks.length - 2][1] = new Block(Block.PLAYER3_HEAD);
                directionPlayer3 = Settings.RIGHT;

                // Imposta il giocatore 4 nell'angolo in basso a destra
                blocks[blocks.length - 2][blocks[1].length - 2] = new Block(Block.PLAYER4_HEAD);
                directionPlayer4 = Settings.LEFT;

            }
        }

        public int getModalitàCorrente () {
            return modalitàCorrente;
        }

        public Block[][] getBlocks () {
            return blocks;
        }


    private int[] getPlayerPosition(int playerIndex) {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j].type() == playerIndex) {
                    return new int[]{i, j}; // Restituisci le coordinate della testa del giocatore
                }
            }
        }
        return new int[]{-1, -1};
    }
    private boolean isCollisionWithOtherPlayer(int newX, int newY, int currentPlayerType) {
        for (int i = 0; i < 4; i++) {
            if (i + 1 != currentPlayerType) {
                int[] playerPosition = getPlayerPosition(i + 1);
                int playerX = playerPosition[0];
                int playerY = playerPosition[1];
                if (playerX == newX && playerY == newY - 1) {
                    return true;
                }
                if (playerX == newX && playerY == newY + 1) {
                    return true;
                }
                if(playerY == newY && playerX == newX + 1){
                    return true;
                }
                if(playerY == newY && playerX == newX - 1){
                    return true;
                }
            }
        }
        return false;
    }
}



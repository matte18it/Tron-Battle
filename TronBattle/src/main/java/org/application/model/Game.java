package org.application.model;

import com.github.pervoj.jiconfont.FontAwesomeSolid;
import jiconfont.swing.IconFontSwing;
import org.application.loop.GameLoop;
import org.application.utility.Settings;
import org.application.view.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private final Random random = new Random();
    private Block[][] blocks = new Block[Settings.WORLD_SIZEX][Settings.WORLD_SIZEY];
    private static final String[] iaNames = new String[]{"_4F", "Dialga", "Palkia", "NonPiùSoli"};
    private List<Integer> alivePlayers;
    private boolean reload = false;
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
               directionPlayer1= iaServices(iaNames[0], directionPlayer1);
                directionPlayer2=iaServices(iaNames[1], directionPlayer2);
                directionPlayer3=iaServices(iaNames[2], directionPlayer3);
                directionPlayer4=iaServices(iaNames[3], directionPlayer4);

                movePlayer(directionPlayer1, Block.PLAYER1_HEAD, Block.PLAYER1_BODY);
                movePlayer(directionPlayer2, Block.PLAYER2_HEAD, Block.PLAYER2_BODY);
                movePlayer( directionPlayer3, Block.PLAYER3_HEAD, Block.PLAYER3_BODY);
                movePlayer( directionPlayer4, Block.PLAYER4_HEAD, Block.PLAYER4_BODY);
            }
        }
        if(alivePlayers.size()==1 && reload){
            int winner = alivePlayers.get(0);
            IconFontSwing.register(FontAwesomeSolid.getIconFont());
            Icon icon = IconFontSwing.buildIcon(FontAwesomeSolid.TROPHY, 40, new Color(255, 215, 0));
            String message = "The winner is player " + iaNames[winner-1] ;
            reload = false;
            JOptionPane.showConfirmDialog(null, message, "THE END", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
            GameFrame.launchMenu();
        }else if(alivePlayers.size()==1 && !reload){
            reload = true;
        }
    }

    private int iaServices(String iaName, int directionPlayer) {
        // la matrice blocks contiene il mondo di gioco
        // ogni IA deve modificare directionPlayer in base alla sua strategia
        switch (iaName){
            case "_4F" -> {
                // TODO IA 4F
            }
            case "Dialga" -> {
               // TODO IA Dialga
            }
            case "Palkia" -> {
               // TODO IA Palkia
            }
            case "NonPiùSoli" -> {
               // TODO IA NonPiùSoli
            }
        }
        return directionPlayer;
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
        alivePlayers.remove((Integer) headType);

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
                alivePlayers = new ArrayList<>(4); // Inizializza i giocatori vivi
                alivePlayers.add(1);
                alivePlayers.add(2);
                alivePlayers.add(3);
                alivePlayers.add(4);
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

    public String[] getRandomizeIA() {
        // Crea un'istanza di Random
        Random random = new Random();

        // Applica l'algoritmo di Fisher-Yates per randomizzare l'array
        for (int i = iaNames.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Scambia l'elemento corrente con uno casuale selezionato precedentemente
            String temp = iaNames[index];
            iaNames[index] = iaNames[i];
            iaNames[i] = temp;
        }

        return iaNames;
    }
}



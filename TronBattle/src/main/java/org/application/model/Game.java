package org.application.model;

import com.github.pervoj.jiconfont.FontAwesomeSolid;
import jiconfont.swing.IconFontSwing;
import org.application.IA.IA_Dialga.MainClassDialga;
import org.application.IA.IA_NonPiuSoli.MainClassNonPiuSoli;
import org.application.IA.IA_Palkia.MainClassPalkia;
import org.application.IA.IA_4F.MainClass4F;
import org.application.utility.Settings;
import org.application.view.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

public class Game {
    public int humanDirection = 1;
    private final Random random = new Random();
    private Block[][] blocks = new Block[Settings.WORLD_SIZEX][Settings.WORLD_SIZEY];
    private List<Integer> alivePlayers;
    Stack<Integer> deadPlayers = new Stack<>();
    private boolean reload = false;
    private int modalitaCorrente;
    private int directionPlayer1;
    private int directionPlayer2;
    private int directionPlayer3;
    private int directionPlayer4;
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private static Game instance;

    public int getDirectionPlayer1() {
        return directionPlayer1;
    }


    public int getDirectionPlayer3() {
        return directionPlayer3;
    }


    public int getDirectionPlayer4() {
        return directionPlayer4;
    }


    public int getDirectionPlayer2() {
        return directionPlayer2;
    }


    private Game() {}

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }


    public void move() {
        switch (modalitaCorrente) {
            case Settings.SINGLE_PLAYER -> {
                long start = System.currentTimeMillis();
                Future<Integer> future1 = executor.submit(() -> iaServices(Settings.iaNames[0], Block.PLAYER1_HEAD, Block.PLAYER1_BODY));
                Future<Integer> future2 = executor.submit(this::humanService);
                try {
                    directionPlayer1 = future1.get();
                    long end = System.currentTimeMillis();
                    System.out.println("Tempo impiegato "+Settings.iaNames[0]+" : " + (end - start) + "ms");
                    directionPlayer2 = future2.get();
                } catch (InterruptedException | ExecutionException  e) {
                    e.printStackTrace();
                }
                movePlayer(directionPlayer1, Block.PLAYER1_HEAD, Block.PLAYER1_BODY);
                movePlayer(directionPlayer2, Block.PLAYER2_HEAD, Block.PLAYER2_BODY);
            }
            case Settings.TWO_PLAYER -> {
                long start = System.currentTimeMillis();
                Future<Integer> future1 = executor.submit(() -> iaServices(Settings.iaNames[0], Block.PLAYER1_HEAD, Block.PLAYER1_BODY));
                Future<Integer> future2 = executor.submit(() -> iaServices(Settings.iaNames[1], Block.PLAYER2_HEAD, Block.PLAYER2_BODY));
                try {
                    directionPlayer1 = future1.get();
                    long end = System.currentTimeMillis();
                    System.out.println("Tempo impiegato "+Settings.iaNames[0]+" : " + (end - start) + "ms");
                    directionPlayer2 = future2.get();
                     end = System.currentTimeMillis();
                    System.out.println("Tempo impiegato "+Settings.iaNames[1]+" : " + (end - start) + "ms");
                } catch (InterruptedException | ExecutionException  e) {
                    e.printStackTrace();
                }
                movePlayer(directionPlayer1, Block.PLAYER1_HEAD, Block.PLAYER1_BODY);
                movePlayer(directionPlayer2, Block.PLAYER2_HEAD, Block.PLAYER2_BODY);
            }
            case Settings.COMPETITION -> {
                long start = System.currentTimeMillis();
                Future<Integer> future1 = executor.submit(() -> iaServices(Settings.iaNames[0], Block.PLAYER1_HEAD, Block.PLAYER1_BODY));
                Future<Integer> future2 = executor.submit(() -> iaServices(Settings.iaNames[1], Block.PLAYER2_HEAD, Block.PLAYER2_BODY));
                Future<Integer> future3 = executor.submit(() -> iaServices(Settings.iaNames[2], Block.PLAYER3_HEAD, Block.PLAYER3_BODY));
                Future<Integer> future4 = executor.submit(() -> iaServices(Settings.iaNames[3], Block.PLAYER4_HEAD, Block.PLAYER4_BODY));
                try {
                    directionPlayer1 = future1.get();
                    long end = System.currentTimeMillis();
                    System.out.println("Tempo impiegato "+Settings.iaNames[0]+" : " + (end - start) + "ms");
                    directionPlayer2 = future2.get();
                     end = System.currentTimeMillis();
                    System.out.println("Tempo impiegato "+Settings.iaNames[1]+" : " + (end - start) + "ms");
                    directionPlayer3 = future3.get();
                     end = System.currentTimeMillis();
                    System.out.println("Tempo impiegato "+Settings.iaNames[2]+" : " + (end - start) + "ms");
                    directionPlayer4 = future4.get();
                     end = System.currentTimeMillis();
                    System.out.println("Tempo impiegato "+Settings.iaNames[3]+" : " + (end - start) + "ms");
                } catch (InterruptedException | ExecutionException  e) {
                    e.printStackTrace();
                }
                movePlayer(directionPlayer1, Block.PLAYER1_HEAD, Block.PLAYER1_BODY);
                movePlayer(directionPlayer2, Block.PLAYER2_HEAD, Block.PLAYER2_BODY);
                movePlayer(directionPlayer3, Block.PLAYER3_HEAD, Block.PLAYER3_BODY);
                movePlayer(directionPlayer4, Block.PLAYER4_HEAD, Block.PLAYER4_BODY);
            }
        }
        controllaVincitore();
    }

    private int iaServices(String iaName, int playerHead, int playerBody) {
        int directionPlayer = 0;
        // la matrice blocks contiene il mondo di gioco
        // ogni IA deve modificare directionPlayer in base alla sua strategia
        switch (iaName){
            case "4F" -> {
                if(analyzeMatrix(getBlocks(), playerHead))
                    directionPlayer = MainClass4F.getInstance().getDirection(getBlocks(), playerHead, playerBody);
                 System.out.println("IA 4F: " + directionPlayer);
                 break;
            }
            case "Dialga" -> {
                if(analyzeMatrix(getBlocks(), playerHead))
                    directionPlayer = MainClassDialga.getInstance().getDirection(getBlocks(), playerHead, playerBody);
                System.out.println("IA Dialga: " + directionPlayer);
                break;
            }
            case "Palkia" -> {
                if(analyzeMatrix(getBlocks(), playerHead))
                    directionPlayer = MainClassPalkia.getInstance().getDirection(getBlocks(), playerHead, playerBody);
                System.out.println("IA Palkia: " + directionPlayer);
                break;
            }
            case "NonPiuSoli" -> {
                if(analyzeMatrix(getBlocks(), playerHead))
                    directionPlayer = MainClassNonPiuSoli.getInstance().getDirection(getBlocks(), playerHead, playerBody);
                System.out.println("IA NonPiuSoli: " + directionPlayer);
                break;
            }
        }
        return directionPlayer;
    }

    private int humanService() {
        return humanDirection;
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
        if(newX < 0 || newX >= blocks.length || newY < 0 || newY >= blocks[0].length || blocks[newX][newY].type() != Block.EMPTY ){
            uccidiGiocatore(headType, bodyType);
            return;}
        blocks[x][y] = new Block(bodyType);
        blocks[newX][newY] = new Block(headType);
    }

    private void uccidiGiocatore(int headType, int bodyType) {
        alivePlayers.remove((Integer) headType);
        if(!deadPlayers.contains(headType))
            deadPlayers.add( headType);


        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j].type() == headType || blocks[i][j].type() == bodyType) {
                    blocks[i][j] = new Block(Block.EMPTY);
                }
            }
        }

    }

    public void setModalitaCorrente ( int modalitaCorrente){
            if(modalitaCorrente == Settings.COMPETITION || modalitaCorrente == Settings.TWO_PLAYER)
                this.getRandomizeIA();
            this.modalitaCorrente = modalitaCorrente;
            this.loadWorld();
        }

        private void loadWorld () {
            for (int i = 0; i < blocks.length; i++) {
                for (int j = 0; j < blocks[i].length; j++) {
                    blocks[i][j] = new Block(Block.EMPTY);
                }
            }
            if (modalitaCorrente == Settings.COMPETITION) {
                alivePlayers = new ArrayList<>(4); // Inizializza i giocatori vivi
                alivePlayers.add(1);
                alivePlayers.add(2);
                alivePlayers.add(3);
                alivePlayers.add(4);
                // Imposta il giocatore 1 nell'angolo in alto a sinistra
                blocks[1][1] = new Block(Block.PLAYER1_HEAD);
                directionPlayer1 = Settings.RIGHT;

                // Imposta il giocatore 2 nell'angolo in alto a destra
                blocks[1][blocks[1].length-2] = new Block(Block.PLAYER2_HEAD);
                directionPlayer2 = Settings.LEFT;

                // Imposta il giocatore 3 nell'angolo in basso a sinistra
                blocks[blocks.length - 2][1] = new Block(Block.PLAYER3_HEAD);
                directionPlayer3 = Settings.RIGHT;

                // Imposta il giocatore 4 nell'angolo in basso a destra
                blocks[blocks.length - 2][blocks[1].length - 2] = new Block(Block.PLAYER4_HEAD);
                directionPlayer4 = Settings.LEFT;
            }
            else {
                alivePlayers = new ArrayList<>(2); // Inizializza i giocatori vivi
                alivePlayers.add(1);
                alivePlayers.add(2);
                // Imposta il giocatore 1 nell'angolo in alto a sinistra
                blocks[1][1] = new Block(Block.PLAYER1_HEAD);
                directionPlayer1 = Settings.RIGHT;
                // Imposta il giocatore 2 nell'angolo in basso a destra
                blocks[blocks.length - 2][blocks[1].length - 2] = new Block(Block.PLAYER2_HEAD);
                directionPlayer2 = Settings.LEFT;
            }
        }

        public int getModalitaCorrente () {
            return modalitaCorrente;
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


    public void getRandomizeIA() {
        // Crea un'istanza di Random
        Random random = new Random();

        // Applica l'algoritmo di Fisher-Yates per randomizzare l'array
        for (int i = Settings.iaNames.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Scambia l'elemento corrente con uno casuale selezionato precedentemente
            String temp = Settings.iaNames[index];
            Settings.iaNames[index] = Settings.iaNames[i];
            Settings.iaNames[i] = temp;
        }
    }
    private void controllaVincitore() {
        if (alivePlayers.isEmpty() && reload) {
            reload = false;
            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("<html><body><h1>Final rankings:</h1>");
            int position = 1;
            int winner = 0;

            // Estrai i giocatori eliminati finché lo stack non è vuoto
            while (!deadPlayers.isEmpty()) {
                Integer player = deadPlayers.pop(); // Prendi il primo giocatore eliminato (l'ultimo inserito)
                messageBuilder.append("<h2>").append(position).append(". Player ").append(Settings.iaNames[player - 1]).append("</h2>");
                if (position == 1) {
                    winner = player;
                }
                position++;
            }

            // Aggiungi anche il vincitore alla classifica
            messageBuilder.append("<h1>The winner is player ").append(Settings.iaNames[winner - 1]).append("</h1></body></html>");

            IconFontSwing.register(FontAwesomeSolid.getIconFont());
            Icon icon = IconFontSwing.buildIcon(FontAwesomeSolid.TROPHY, 40, new Color(255, 215, 0));
            JOptionPane.showConfirmDialog(null, messageBuilder.toString(), "THE END", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
            GameFrame.launchMenu();
        } else if (alivePlayers.isEmpty()) {
            reload = true;
        }
        if(alivePlayers.size()==1){
            deadPlayers.add(alivePlayers.get(0));
            alivePlayers.clear();
            reload = true;
        }
    }

    public String[] getIA() {
        return Settings.iaNames;
    }

    public boolean analyzeMatrix(Block[][] blocks, int playerHead) {
        // cerco se trovo il player interessato
        for(int i = 0; i < blocks.length; i++) {
            for(int j = 0; j < blocks[i].length; j++) {
                if(blocks[i][j].type() == playerHead)
                    return true;
            }
        }

        return false;
    }
}



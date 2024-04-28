package org.application.view;

import org.application.controller.MovementController;
import org.application.loop.GameLoop;
import org.application.model.Game;
import org.application.utility.PlayWav;
import org.application.utility.Settings;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JPanel {
    // Attributi
    private static final PlayWav playMenuMusic = PlayWav.getInstance();
    private static final JFrame frameGame = new JFrame("Menu Principale");
    private static MenuView menuView;
    private static GameView GAME_VIEW;
    private static GameMenuView GAME_MENU_VIEW;
    private static GameLoop gameLoop = null;

    //Metodi
    public static void launchMenu() {
        menuView = new MenuView();
        if(GAME_VIEW != null && GAME_VIEW.isShowing()) {
            frameGame.getContentPane().removeAll();
            frameGame.setVisible(false);
            frameGame.setSize(Settings.WINDOW_SIZEX, Settings.WINDOW_SIZEY);
            gameLoop.stopGame();
            frameGame.add(menuView);
            frameGame.revalidate();
            frameGame.repaint();
            frameGame.setVisible(true);
        }   // se è attiva la schermata di gioco, la elimino
        else{
            frameGame.add(menuView);
            frameGame.revalidate();
            frameGame.repaint();
        }
        frameGame.setTitle("Tron Battle - Menu");
        frameGame.setFocusable(true);
        frameGame.requestFocus();

        if(!Settings.access){
            Settings.access = true;
            init();
            frameGame.setUndecorated(true);
            frameGame.setVisible(true);
            // Faccio partire la musica
            if(!playMenuMusic.isPlay()){ playMenuMusic.play(); }
        }   // eseguito solo all'accesso
    }

    public static void launchGame() {
        Game.getInstance().setModalitaCorrente(Settings.COMPETITION);
        frameGame.setTitle("Tron Battle - Competition");
        initGameScene();
    }

    public static void singlePlayer() {
        Game.getInstance().setModalitaCorrente(Settings.SINGLE_PLAYER);
        frameGame.setTitle("Tron Battle - Single Player");
        initGameScene();
    }

    public static void twoPlayer() {
        Game.getInstance().setModalitaCorrente(Settings.TWO_PLAYER);
        frameGame.setTitle("Tron Battle - IA VS IA");
        initGameScene();
    }

    public static void initGameScene() {
        // stampo il log
        System.out.println("----- LOG PARTITA " + Settings.log + " -----");
        Settings.log++;

        GAME_VIEW = new GameView();
        GAME_MENU_VIEW = new GameMenuView();
        frameGame.remove(menuView);
        MovementController controller = new MovementController(GAME_VIEW);
        gameLoop = new GameLoop(controller);
        GAME_VIEW.setController(controller);
        GAME_VIEW.setFocusable(true);
        GAME_VIEW.requestFocus();
        GAME_VIEW.setSize(Settings.WINDOW_SIZEX, Settings.WINDOW_SIZEY);
        frameGame.setSize(Settings.WINDOW_SIZEX, Settings.WINDOW_SIZEY+40);
        GAME_MENU_VIEW.init();
        frameGame.add(GAME_VIEW);
        frameGame.add(GAME_MENU_VIEW, BorderLayout.NORTH);
        gameLoop.startGame();
        frameGame.revalidate();
        frameGame.repaint();
    }

    private static void init() {
        //Con questo setto la dimensione e la posizione della finestra di gioco
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenDimension = toolkit.getScreenSize();
        frameGame.setSize(Settings.WINDOW_SIZEX, Settings.WINDOW_SIZEY);

        // Mettiamo la finestra al centro dello schermo
        int x = (screenDimension.width - frameGame.getWidth())/2;
        int y = (screenDimension.height - frameGame.getHeight())/2;
        frameGame.setLocation(x, y);
    }
}

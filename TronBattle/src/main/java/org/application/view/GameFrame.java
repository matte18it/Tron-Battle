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
    private static final MenuView menuView = new MenuView();
    private static final GameView GAME_VIEW = new GameView();

    //Metodi
    public static void launchMenu() {
        init(); // metodo per settare i settings della finestra
        frameGame.add(menuView);

        frameGame.setUndecorated(true);
        frameGame.setVisible(true);
        frameGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Faccio partire la musica
        if(!playMenuMusic.isPlay()){ playMenuMusic.play(); }
    }

    public static void launchGame() {
        Game.getInstance().setModalitaCorrente(Settings.COMPETITION);
        frameGame.remove(menuView);
        init(); // metodo per settare i settings della finestra
        frameGame.setTitle("Tron Battle - Competition");
        MovementController controller = new MovementController(GAME_VIEW);
        GameLoop gameLoop = new GameLoop(controller);
        GAME_VIEW.setController(controller);
        GAME_VIEW.setFocusable(true);
        GAME_VIEW.requestFocus();
        frameGame.add(GAME_VIEW);
        gameLoop.startGame();
        frameGame.revalidate();
        frameGame.repaint();
    }

    public static void singlePlayer() {
        Game.getInstance().setModalitaCorrente(Settings.SINGLE_PLAYER);
        frameGame.remove(menuView);
        init(); // metodo per settare i settings della finestra
        frameGame.setTitle("Tron Battle - Single Player");
        frameGame.setFocusable(true);
        frameGame.requestFocus();
        frameGame.add(GAME_VIEW);
        frameGame.revalidate();
        frameGame.repaint();
    }

    public static void twoPlayer() {
        Game.getInstance().setModalitaCorrente(Settings.TWO_PLAYER);
        frameGame.remove(menuView);
        init(); // metodo per settare i settings della finestra
        frameGame.setTitle("Tron Battle - IA VS IA");
        frameGame.setFocusable(true);
        frameGame.requestFocus();
        frameGame.add(GAME_VIEW);
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

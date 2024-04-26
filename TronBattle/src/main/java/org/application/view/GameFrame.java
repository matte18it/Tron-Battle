package org.application.view;

import org.application.controller.MovementController;
import org.application.controller.TwoIAController;
import org.application.loop.GameLoop;
import org.application.utility.Settings;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JPanel {
    // Attributi
    private static final JFrame frameGame = new JFrame("Menu Principale");
    private static final MenuView menuView = new MenuView();
    private static final GameView gameView = new GameView();
    private static final SinglePlayerView singlePlayerView = new SinglePlayerView();
    private static final TwoIAView twoPlayerView = new TwoIAView();

    //Metodi
    public static void launchMenu() {
        init(); // metodo per settare i settings della finestra
        frameGame.add(menuView);

        frameGame.setUndecorated(true);
        frameGame.setVisible(true);
        frameGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void launchGame() {
        frameGame.remove(menuView);
        init(); // metodo per settare i settings della finestra
        frameGame.setTitle("Tron Battle - Competition");
        MovementController controller = new MovementController(gameView);
        GameLoop gameLoop = new GameLoop(controller);
        gameView.setController(controller);
        gameView.setFocusable(true);
        gameView.requestFocus();
        frameGame.add(gameView);
        gameLoop.startGame();
        frameGame.revalidate();
        frameGame.repaint();
    }

    public static void singlePlayer() {
        frameGame.remove(menuView);
        init(); // metodo per settare i settings della finestra
        frameGame.setTitle("Tron Battle - Single Player");
        frameGame.setFocusable(true);
        frameGame.requestFocus();
        frameGame.add(singlePlayerView);
        frameGame.revalidate();
        frameGame.repaint();
    }

    public static void twoPlayer() {
        frameGame.remove(menuView);
        init(); // metodo per settare i settings della finestra
        frameGame.setTitle("Tron Battle - Single Player");
        frameGame.setFocusable(true);
        frameGame.requestFocus();
        frameGame.add(twoPlayerView);
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

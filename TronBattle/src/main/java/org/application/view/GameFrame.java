package org.application.view;

import org.application.controller.MovementController;
import org.application.loop.GameLoop;
import org.application.utility.Settings;

import javax.swing.*;

public class GameFrame extends JPanel {
    public static void launch() {
        JFrame frame = new JFrame("Pacman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Settings.WINDOW_SIZEX, Settings.WINDOW_SIZEY);
        frame.setVisible(true);
        GameGraphics gameGraphics = new GameGraphics();
        MovementController controller = new MovementController(gameGraphics);
        GameLoop gameLoop = new GameLoop(controller);
        gameGraphics.setController(controller);
        gameGraphics.setFocusable(true);
        gameGraphics.requestFocus();
        frame.add(gameGraphics);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        gameLoop.startGame();
    }
}

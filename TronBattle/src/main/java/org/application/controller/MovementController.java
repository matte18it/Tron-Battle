package org.application.controller;

import org.application.model.Game;
import org.application.view.GameView;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MovementController extends KeyAdapter {
    private GameView gameView;

    public MovementController(GameView gameView) {
        this.gameView = gameView;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);

         Game.getInstance().humanDirection = switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> org.application.utility.Settings.LEFT;
            case KeyEvent.VK_RIGHT -> org.application.utility.Settings.RIGHT;
            case KeyEvent.VK_DOWN-> org.application.utility.Settings.DOWN;
            case KeyEvent.VK_UP -> org.application.utility.Settings.UP;
            default -> throw new IllegalStateException("Unexpected value: " + e.getKeyCode());
        };
    }

    public void update() {
        Game.getInstance().move();
        gameView.repaint();
    }

}

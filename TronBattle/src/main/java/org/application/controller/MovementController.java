package org.application.controller;

import org.application.view.CompetitionView;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.application.model.Game;

public class MovementController extends KeyAdapter {
    private CompetitionView competitionView;

    public MovementController(CompetitionView competitionView) {
        this.competitionView = competitionView;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);

        Object Settings;
        int direction = switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> org.application.utility.Settings.MOVE_LEFT;
            case KeyEvent.VK_RIGHT -> org.application.utility.Settings.MOVE_RIGHT;
            case KeyEvent.VK_DOWN-> org.application.utility.Settings.MOVE_DOWN;
            case KeyEvent.VK_UP -> org.application.utility.Settings.MOVE_UP;
            default -> throw new IllegalStateException("Unexpected value: " + e.getKeyCode());
        };

        Game.getInstance().setCurrentDirection(direction);
    }

    public void update() {
        Game.getInstance().move();
        competitionView.repaint();
    }

}

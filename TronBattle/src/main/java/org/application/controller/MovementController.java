package org.application.controller;

import org.application.view.GameView;

import java.awt.event.KeyAdapter;

public class MovementController extends KeyAdapter {
    private GameView gameView;

    public MovementController(GameView gameView) {
        this.gameView = gameView;
    }

}

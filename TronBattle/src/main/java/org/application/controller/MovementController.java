package org.application.controller;

import org.application.view.GameGraphics;

import java.awt.event.KeyAdapter;

public class MovementController extends KeyAdapter {
    private GameGraphics gameGraphics;

    public MovementController(GameGraphics gameGraphics) {
        this.gameGraphics = gameGraphics;
    }

}

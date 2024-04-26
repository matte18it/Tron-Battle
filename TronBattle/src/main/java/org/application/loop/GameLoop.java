package org.application.loop;

import org.application.controller.MovementController;

import java.util.concurrent.ScheduledExecutorService;

public class GameLoop {
    private MovementController controller;
    private ScheduledExecutorService executor;
    public GameLoop(MovementController controller) {
        this.controller = controller;
    }

    public void startGame() {
    }
}

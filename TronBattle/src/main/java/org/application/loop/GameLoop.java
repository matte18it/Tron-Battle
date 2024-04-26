package org.application.loop;

import org.application.controller.MovementController;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameLoop {
    private MovementController controller;
    private ScheduledExecutorService executor;
    public GameLoop(MovementController controller) {
        this.controller = controller;
    }

    public void startGame() {
          if(executor != null)
            return;
    executor = Executors.newSingleThreadScheduledExecutor();

        executor.scheduleAtFixedRate(() -> controller.update(), 0, 200, TimeUnit.MILLISECONDS);
}}

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

        Runnable task = new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                try {
                    controller.update();
                } catch (Exception e) {
                    e.printStackTrace(); // Handle exceptions appropriately
                } finally {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    long delay = Math.max(200 - elapsedTime, 0);
                    executor.schedule(this, delay, TimeUnit.MILLISECONDS);
                }
            }
        };

        // Initial scheduling of the task
        executor.schedule(task, 0, TimeUnit.MILLISECONDS);
    }

    public void stopGame() {
        if(executor == null)
            return;
        executor.shutdown();
        executor = null;
    }
}

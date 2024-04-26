package org.application.model;

public class Game {
    private int currentDirection;
    private Game(){};
    private static Game instance;
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void setCurrentDirection(int direction) {
        this.currentDirection = direction;
    }

    public void move() {
    }
}

package org.application.model;

import org.application.utility.Settings;

public class Game {
    private Block[][] blocks = new Block[Settings.CELL_SIZEX][Settings.CELL_SIZEY];
    private int x;
    private int y;
    private int currentDirection = Settings.MOVE_RIGHT;
    private Game(){
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                blocks[i][j] = new Block(Block.EMPTY);
            }
        }
    };
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

package org.application.IA.IA_Palkia.Research.Support;

public class PlayerPositionType {
    private int x;
    private int y;
    private int id;

    public PlayerPositionType(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }
}

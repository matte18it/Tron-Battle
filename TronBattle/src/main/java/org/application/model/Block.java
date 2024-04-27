package org.application.model;

public record Block(int type) {

    public static final int EMPTY = 0;
    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    public static final int PLAYER3 = 3;
    public static final int PLAYER4 = 4;

}

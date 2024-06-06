package org.application.model;

public record Block(int type) {

    public static final int EMPTY = 0;
    public static final int PLAYER1_HEAD = 1;
    public static final int PLAYER2_HEAD = 2;
    public static final int PLAYER3_HEAD = 3;
    public static final int PLAYER4_HEAD = 4;
    public static final int PLAYER1_BODY = 5;
    public static final int PLAYER2_BODY = 6;
    public static final int PLAYER3_BODY = 7;
    public static final int PLAYER4_BODY = 8;

}

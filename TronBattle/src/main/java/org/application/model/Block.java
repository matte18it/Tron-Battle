package org.application.model;

public record Block(int type) {

    public static final int EMPTY = 0;
    public static final int PACMAN = 1;
    public static final int POINT = 2;
    public static final int WALL = 3;

}

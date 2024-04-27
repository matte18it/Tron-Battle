package org.application.utility;

public class Settings {
    public  final static int WORLD_SIZEX=64;
    public  final static int WORLD_SIZEY=40;
    public  final static int WINDOW_SIZEX=1280;
    public  final static int WINDOW_SIZEY=640;
    public  final static int CELL_SIZEX = WINDOW_SIZEX / WORLD_SIZEX; //32
    public  final static int CELL_SIZEY = WINDOW_SIZEY / WORLD_SIZEY; // 16
    public final static int MOVE_RIGHT = 0;
    public final static int MOVE_LEFT = 1;
    public final static int MOVE_UP = 2;
    public final static int MOVE_DOWN = 3;

    // variabili per la IA
    public String SinglePlayerIA = "";
    public String TwoPlayer_FirstIA = "";
    public String TwoPlayer_SecondIA = "";
}

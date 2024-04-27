package org.application.utility;

public class Settings {
    public  final static int WORLD_SIZEX=64;
    public  final static int WORLD_SIZEY=40;
    public  final static int WINDOW_SIZEX=1280;
    public  final static int WINDOW_SIZEY=640;
    public  final static int CELL_SIZEX = WINDOW_SIZEX / WORLD_SIZEX; //20
    public  final static int CELL_SIZEY = WINDOW_SIZEY / WORLD_SIZEY; // 16
    public final static int RIGHT = 0;
    public final static int LEFT = 1;
    public final static int UP = 2;
    public final static int DOWN = 3;

    // variabili per la IA
    public static String SinglePlayerIA = "";
    public static String TwoPlayer_FirstIA = "";
    public static String TwoPlayer_SecondIA = "";

    // variabili per la modalità
    public static final int SINGLE_PLAYER = 0;
    public static final int TWO_PLAYER = 1;
    public static final int COMPETITION = 2;
}

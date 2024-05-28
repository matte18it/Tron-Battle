package org.application.IA.IA_Palkia.Research.Support;

public class Cell {

    public int x, y, c;
    public Cell(int x, int y, int c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", c=" + c +
                '}';
    }
}

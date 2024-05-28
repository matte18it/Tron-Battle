package org.application.IA.IA_4F;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("count_4F")
public class Count {
    @Param(0)
    private int dx;

    @Param(1)
    private int sx;

    @Param(2)
    private int up;
    @Param(3)
    private int down;

    public Count(int dx, int sx, int up, int down) {
        this.dx = dx;
        this.sx = sx;
        this.up = up;
        this.down = down;
    }
    public Count() {}

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getSx() {
        return sx;
    }

    public void setSx(int sx) {
        this.sx = sx;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }
}

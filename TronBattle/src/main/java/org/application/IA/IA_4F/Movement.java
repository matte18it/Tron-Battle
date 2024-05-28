package org.application.IA.IA_4F;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("movement_4F")
public class Movement {
    @Param(0)
    private int row;
    @Param(1)
    private int column;
    @Param(2)
    private int direction;
    public Movement() {}
    public Movement(int row, int column, int direction) {
        this.row = row;
        this.column = column;
        this.direction = direction;
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getColumn() {
        return column;
    }
    public void setColumn(int column) {
        this.column = column;
    }
    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }

}

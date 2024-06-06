package org.application.IA.IA_4F;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("position_4F")
public class Position {
    @Param(0)
    private static int row;
    @Param(1)
    private static int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }
    public Position() {}

    public int getColumn() {
        return column;
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setColumn(int column) {
        this.column = column;
    }
}

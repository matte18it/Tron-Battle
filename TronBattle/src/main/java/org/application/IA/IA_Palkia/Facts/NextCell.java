package org.application.IA.IA_Palkia.Facts;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.Data;

@Data
@Id("nextCellPalkia")
public class NextCell {
    @Param(0)
    public int x;
    @Param(1)
    public int y;

    public NextCell() {
    }
    public NextCell(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

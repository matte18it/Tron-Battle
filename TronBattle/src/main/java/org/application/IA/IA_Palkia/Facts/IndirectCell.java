package org.application.IA.IA_Palkia.Facts;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.Data;

@Data
@Id("indirectCellPalkia")
public class IndirectCell {
    @Param(0)
    public int x;
    @Param(1)
    public int y;
    @Param(2)
    public int c;

    public IndirectCell() {}
    public IndirectCell(int x, int y, int c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }
}

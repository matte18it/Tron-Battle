package org.application.IA.IA_Palkia.Facts;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Id("freeAdjacentPalkia")
public class FreeAdjacent {
    @Param(0)
    private int x;
    @Param(1)
    private int y;
    @Param(2)
    private int c;

    public FreeAdjacent() {}
    public FreeAdjacent(int x, int y, int c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }
}

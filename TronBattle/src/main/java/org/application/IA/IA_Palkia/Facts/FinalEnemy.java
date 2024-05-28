package org.application.IA.IA_Palkia.Facts;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Id("finalEnemyPalkia")
public class FinalEnemy {
    @Param(0)
    private int x;
    @Param(1)
    private int y;

    public FinalEnemy() {}
    public FinalEnemy(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

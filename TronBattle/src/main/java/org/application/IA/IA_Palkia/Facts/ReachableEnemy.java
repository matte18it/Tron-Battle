package org.application.IA.IA_Palkia.Facts;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.Data;

@Data
@Id("reachableEnemyPalkia")
public class ReachableEnemy {
    @Param(0)
    private int x;
    @Param(1)
    private int y;

    public ReachableEnemy() {}
    public ReachableEnemy(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

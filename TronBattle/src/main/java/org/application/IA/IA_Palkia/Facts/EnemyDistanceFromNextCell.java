package org.application.IA.IA_Palkia.Facts;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Id("enemyDistanceFromNextCellPalkia")
public class EnemyDistanceFromNextCell {
    @Param(0)
    private int x;
    @Param(1)
    private int y;
    @Param(2)
    private int distance;

    public EnemyDistanceFromNextCell() {}
    public EnemyDistanceFromNextCell(int x, int y, int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }
}

package org.application.IA.IA_Palkia.Facts;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Id("minDistancePalkia")
public class MinDistance {
    @Param(0)
    public int x;
    @Param(1)
    public int y;
    @Param(2)
    public int minDistance;

    public MinDistance() {
    }
    public MinDistance(int x, int y, int minDistance) {
        this.x = x;
        this.y = y;
        this.minDistance = minDistance;
    }
}

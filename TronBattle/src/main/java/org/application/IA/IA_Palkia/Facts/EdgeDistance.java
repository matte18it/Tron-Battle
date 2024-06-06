package org.application.IA.IA_Palkia.Facts;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.Data;

@Data
@Id("edgeDistancePalkia")
public class EdgeDistance {
    @Param(0)
    private int x;
    @Param(1)
    private int y;
    @Param(2)
    private int distance;

    public EdgeDistance() {}
    public EdgeDistance(int x, int y, int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }
}

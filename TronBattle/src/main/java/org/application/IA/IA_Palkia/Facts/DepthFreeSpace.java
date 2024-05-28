package org.application.IA.IA_Palkia.Facts;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Id("depthFreeSpacePalkia")
public class DepthFreeSpace {
    @Param(0)
    private int x;
    @Param(1)
    private int y;
    @Param(2)
    private int distance;

    public DepthFreeSpace() {
    }
    public DepthFreeSpace(int x, int y, int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }
}

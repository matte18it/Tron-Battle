package org.application.IA.IA_Dialga;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CellUtilDialga {
    int x;
    int y;
    int blockType;
    int distance;

    public CellUtilDialga(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

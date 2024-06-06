package org.application.IA.IA_NonPiuSoli;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Id("playerPosition")
public class PlayerPosition {
    @Param(0)
    private int x;
    @Param(1)
    private int y;

    @Override
    public String toString() {
        return "PlayerPosition{x=" + x + ", y=" + y + "}";
    }
}
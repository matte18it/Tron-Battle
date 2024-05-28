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
@Id("move")
public class Move {

    @Param(0)
    private int direction;

    @Override
    public String toString() {
        return "Move{" + direction + "}";
    }
}


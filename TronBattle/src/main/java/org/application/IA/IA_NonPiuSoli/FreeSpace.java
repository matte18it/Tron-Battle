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
@Id("free_space")
public class FreeSpace {
    @Param(0)
    private int space;
    @Param(1)
    private int direction;

    @Override
    public String toString() {
        return "free_space(" + space + "," + direction + ").";
    }
}

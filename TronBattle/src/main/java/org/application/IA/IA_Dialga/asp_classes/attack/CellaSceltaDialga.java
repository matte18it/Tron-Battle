package org.application.IA.IA_Dialga.asp_classes.attack;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Id("cellaSceltaDialga")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CellaSceltaDialga {
    @Param(0)
    int x;
    @Param(1)
    int y;
}

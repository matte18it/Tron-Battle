package org.application.IA.IA_Dialga.asp_classes.attack;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Id("cellaDialga")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CellaDialga {
    @Param(0)
    int x;
    @Param(1)
    int y;
    @Param(2)
    int distanzaNemico=0;
    @Param(3)
    int distanzaDaMe;
    @Param(4)
    int celleRaggiugibiliNemico;
    @Param(5)
    int celleRaggiugibiliMe;
    @Param(6)
    int numeroNemici;
}

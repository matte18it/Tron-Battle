package org.application.IA.IA_Dialga.asp_classes.end_game_fast;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import it.unical.mat.embasp.languages.asp.SymbolicConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Id("celleNonRaggiungibiliDialga")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CelleNonRaggiungibiliDialga {
    @Param(0)
    SymbolicConstant direction;
    @Param(1)
    int n;
}

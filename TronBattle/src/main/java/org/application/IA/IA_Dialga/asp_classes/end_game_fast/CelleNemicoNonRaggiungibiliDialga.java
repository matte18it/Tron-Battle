package org.application.IA.IA_Dialga.asp_classes.end_game_fast;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import it.unical.mat.embasp.languages.asp.SymbolicConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Id("celleNemicoNonRaggiungibiliDialga")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CelleNemicoNonRaggiungibiliDialga {
    @Param(0)
    int id;
    @Param(1)
    SymbolicConstant direction;
    @Param(2)
    int n;
}

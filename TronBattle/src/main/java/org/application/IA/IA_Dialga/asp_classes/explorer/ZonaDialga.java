package org.application.IA.IA_Dialga.asp_classes.explorer;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import it.unical.mat.embasp.languages.asp.SymbolicConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Id("zonaDialga")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZonaDialga {
    @Param(0)
    private SymbolicConstant nome;
    @Param(1)
    private int numeroAvversari;
    @Param(2)
    private int distanza;
    @Param(3)
    private int numeroCelleRaggiungibli;
    @Param(4)
    private int numeroCelleTotali;
}

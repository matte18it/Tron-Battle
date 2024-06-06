package org.application.IA.IA_Dialga.asp_classes.explorer;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import it.unical.mat.embasp.languages.asp.SymbolicConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Id("zonaSceltaDialga")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZonaSceltaDialga {
    @Param(0)
    private SymbolicConstant nome;
}

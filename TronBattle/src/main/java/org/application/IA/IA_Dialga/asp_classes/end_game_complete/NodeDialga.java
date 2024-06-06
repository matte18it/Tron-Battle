package org.application.IA.IA_Dialga.asp_classes.end_game_complete;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Id("nodeDialga")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NodeDialga {
    @Param(0)
    private int x;
    @Param(1)
    private int y;
}

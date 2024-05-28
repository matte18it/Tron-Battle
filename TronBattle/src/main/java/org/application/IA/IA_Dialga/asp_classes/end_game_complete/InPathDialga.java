package org.application.IA.IA_Dialga.asp_classes.end_game_complete;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.*;

@Id("inPathDialga")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InPathDialga {
    @Param(0)
    private int x;
    @Param(1)
    private int y;
    @Param(2)
    private int x1;
    @Param(3)
    private int y1;
}

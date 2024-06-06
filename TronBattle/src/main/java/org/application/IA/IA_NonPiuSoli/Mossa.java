package org.application.IA.IA_NonPiuSoli;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Id("mossa")
public class Mossa {
    @Param(0)
    private int mossa;

    public Mossa() {
    }

    public Mossa(int mossa) {
        this.mossa = mossa;
    }

    @Override
    public String toString() {
        return "Mossa{" + mossa + "}";
    }
}

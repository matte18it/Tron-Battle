package org.application.IA.IA_NonPiuSoli;

import org.application.model.Block;

import java.util.Random;

public class MainClassNonPiuSoli {
    //Attributi

    //Costruttore

    //Metodi
    public int getDirection(Block[][] blocks) {
        // Questo metodo ritorna la direzione in cui il personaggio deve muoversi
        // 0 = destra, 1 = sinistra, 2 = su, 3 = giù
        return new Random().nextInt(4);
    }
}

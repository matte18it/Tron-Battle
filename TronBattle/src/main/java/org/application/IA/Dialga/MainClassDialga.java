package org.application.IA.Dialga;

import java.util.Random;

public class MainClassDialga {
    //Attributi

    //Costruttore

    //Metodi
    public int getDirection() {
        // Questo metodo ritorna la direzione in cui il personaggio deve muoversi
        return new Random().nextInt(4);
    }
}

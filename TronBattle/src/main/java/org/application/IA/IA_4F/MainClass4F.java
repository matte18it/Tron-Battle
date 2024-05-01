package org.application.IA.IA_4F;


import org.application.model.Block;

import java.util.Random;

public class MainClass4F {
    //Attributi
    private static MainClass4F instance = null;

    //Costruttore
    private MainClass4F() {}

    //Metodi
    public static MainClass4F getInstance() {
        // funzione per ottenere l'istanza del MainClass4F
        if(instance == null)
            instance = new MainClass4F();

        return instance;
    }

    public void init(){
        // metodo per inizializzare i file DLV dell'IA
    }


    public int getDirection(Block[][] blocks, int playerHead, int playerBody) {
        // Questo metodo ritorna la direzione in cui il personaggio deve muoversi
        // 0 = destra, 1 = sinistra, 2 = su, 3 = giù
        return new Random().nextInt(4);
    }
}

package org.application.IA.IA_Dialga;

import org.application.IA.IA_4F.MainClass4F;
import org.application.model.Block;

import java.util.Random;

public class MainClassDialga {
    //Attributi
    private static MainClassDialga instance = null;

    //Costruttore
    private MainClassDialga() {}

    //Metodi
    public static MainClassDialga getInstance() {
        // funzione per ottenere l'istanza del MainClass4F
        if(instance == null)
            instance = new MainClassDialga();

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

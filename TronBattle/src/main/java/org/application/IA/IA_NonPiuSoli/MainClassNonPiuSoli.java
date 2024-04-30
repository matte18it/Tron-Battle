package org.application.IA.IA_NonPiuSoli;

import org.application.model.Block;

import java.util.Random;

public class MainClassNonPiuSoli {
    //Attributi
    private static MainClassNonPiuSoli instance = null;

    //Costruttore
    private MainClassNonPiuSoli() {}

    //Metodi
    public static MainClassNonPiuSoli getInstance() {
        // funzione per ottenere l'istanza del MainClass4F
        if(instance == null)
            instance = new MainClassNonPiuSoli();

        return instance;
    }

    public void init(){
        // metodo per inizializzare i file DLV dell'IA
    }

    public int getDirection(Block[][] blocks) {
        // Questo metodo ritorna la direzione in cui il personaggio deve muoversi
        // 0 = destra, 1 = sinistra, 2 = su, 3 = giù
        return new Random().nextInt(4);
    }
}

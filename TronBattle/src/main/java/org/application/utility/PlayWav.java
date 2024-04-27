package org.application.utility;

import javax.sound.sampled.Clip;

public class PlayWav {
    // Attributi
    private static PlayWav playWav = null;
    private static Clip music;

    // Costruttore
    private PlayWav() {}

    // Metodi
    public static PlayWav getInstance() {
        if(playWav == null)
            playWav = new PlayWav();

        return playWav;
    }

}

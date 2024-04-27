package org.application.utility;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class PlayWav {
    // Attributi
    private static PlayWav play = null;
    private static Clip clip = null;
    private static FloatControl music;

    // Costruttore
    private PlayWav() {}

    // Metodi
    public static PlayWav getInstance() {
        if(play == null)
            play = new PlayWav();

        return play;
    }   // funzione per ottenere l'istanza di PlayWav
    public void play() {
        //carico la musica, la avvio e la setto in loop. Infine prendo il controllore della musica
        String[] paths = {"/music/music1.wav", "/music/music2.wav", "/music/music3.wav"};
        String path = paths[(int) (Math.random() * paths.length)];

        clip = ResourceLoader.getInstance().getAudioClip(path);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        music = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        setVolume(0);
    }   // funzione per riprodurre un file audio
    public void stop(){
        //stoppo la musica
        clip.stop();
    }   // funzione per fermare la riproduzione di un file audio
    public boolean isPlay(){
        //restituisco se la musica è avviata oppure no
        if(clip != null)
            return clip.isRunning();
        return false;
    }   // funzione per controllare se un file audio è in riproduzione
    public void setVolume(int value){
        //setto volume della musica
        music.setValue(value);
    }   // funzione per impostare il volume di un file audio
}

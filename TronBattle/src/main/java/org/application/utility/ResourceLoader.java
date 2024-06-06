package org.application.utility;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;

public class ResourceLoader {
    // Attributi
    private static ResourceLoader instance = null;

    // Costruttore
    private ResourceLoader() {}

    // Metodi
    public static ResourceLoader getInstance() {
        if(instance == null)
            instance = new ResourceLoader();

        return instance;
    }   // funzione per ottenere l'istanza del ResourceLoader
    public BufferedImage getBufferedImage(String s, int width, int height, boolean b) {
        BufferedImage image=null;
        BufferedImage dimg=null;
        try{
            image= ImageIO.read(getClass().getResourceAsStream(s));
            Image tmp=image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            dimg=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = dimg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();


        } catch (IOException | IllegalArgumentException e){
            System.out.println("Error: " + e);
        }
        return  dimg;
    }   // funzione per caricare un'immagine
    public Font getFont(String path, int size, int type) {
        Font font=null;
        try{
            font = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(getClass().getResourceAsStream(path))).deriveFont(type,size);
        }catch (IOException | FontFormatException e){
            System.out.println("Error: " + e);
        }
        return font;
    }   // funzione per caricare un font
    public Clip getAudioClip(String path){
        AudioInputStream audioIn;
        Clip clip=null;
        try {
            audioIn = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error: " + e);
        } return clip;
    }   // funzione per caricare un clip audio
}

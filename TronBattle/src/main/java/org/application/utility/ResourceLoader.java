package org.application.utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    }
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
    }
}

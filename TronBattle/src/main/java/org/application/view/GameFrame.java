package org.application.view;

import org.application.utility.Settings;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JPanel {
    // Attributi
    private static final JFrame frameGame = new JFrame("Menu Principale");

    //Metodi
    public static void launchMenu() {
        init(); // metodo per settare i settings della finestra
    }

    public static void launch() {
        init(); // metodo per settare i settings della finestra
    }

    private static void init() {
        //Con questo setto la dimensione e la posizione della finestra di gioco
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenDimension = toolkit.getScreenSize();
        frameGame.setSize(1280, 660);
        // Mettiamo la finestra al centro dello schermo
        int x = (screenDimension.width - frameGame.getWidth())/2;
        int y = (screenDimension.height - frameGame.getHeight())/2;
        frameGame.setLocation(x, y);

        frameGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameGame.setUndecorated(true);
        frameGame.setVisible(true);
    }
}

package org.application.view;

import org.application.model.Game;
import org.application.utility.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameMenuView extends JPanel {
    JLabel label1= new JLabel();
    JLabel label2= new JLabel();
    JLabel label3= new JLabel();
    JLabel label4= new JLabel();

    GameMenuView() {


        setPreferredSize(new Dimension(Settings.WINDOW_SIZEX, 40)); // Imposta l'altezza del pannello a 30px
        setLayout(new GridLayout(2, 2)); // Utilizza un GridLayout con 2 righe e 2 colonne
        setBackground(Color.BLACK);
        // Crea le etichette per le stringhe
        JLabel label1B = new JLabel("");
        label1 = new JLabel("");
        label1.setForeground(Color.GREEN);
        label1.setOpaque(false); // Imposta il background trasparente
        label1.setIcon(createColorIcon(Color.GREEN)); // Aggiunge un'icona colorata affianco al testo
        label1B.add(label1);
        JLabel label2B = new JLabel("");
        label2 = new JLabel("");
        label2.setForeground(Color.ORANGE);
        label2.setOpaque(false); // Imposta il background trasparente
        label2.setIcon(createColorIcon(Color.ORANGE)); // Aggiunge un'icona colorata affianco al testo
        label2B.add(label2);
        JLabel label3B = new JLabel("");
        label3 = new JLabel("");
        label3.setForeground(Color.WHITE);
        label3.setOpaque(false); // Imposta il background trasparente
        label3.setIcon(createColorIcon(Color.WHITE)); // Aggiunge un'icona colorata affianco al testo
        label3B.add(label3);
        JLabel label4B = new JLabel("");
        label4 = new JLabel("");
        label4.setForeground(Color.BLUE);
        label4.setOpaque(false); // Imposta il background trasparente
        label4.setIcon(createColorIcon(Color.BLUE)); // Aggiunge un'icona colorata affianco al testo
        label4B.add(label4);

        // Imposta il testo allineato a destra per le etichette posizionate a destra
        label2.setHorizontalAlignment(SwingConstants.RIGHT);
        label4.setHorizontalAlignment(SwingConstants.RIGHT);
        // Aggiungi il bordo di 30px alla destra delle label
        label2.setBorder(new EmptyBorder(0, 0, 0, 30));
        label4.setBorder(new EmptyBorder(0, 0, 0, 30));
        label1.setBorder(new EmptyBorder(0, 30, 0, 0));
        label3.setBorder(new EmptyBorder(0, 30, 0, 0));
        // Aggiungi le etichette nei quadranti desiderati del GridLayout
        add(label1);
        add(label2);
        add(label3);
        add(label4);


    }


    private Icon createColorIcon(Color color) {
        int size = 10; // Dimensione del pallino
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(color);
        g2d.fillOval(0, 0, size, size);
        g2d.dispose();
        return new ImageIcon(image);
    }

    public void init() {

        // Ottieni i nomi randomizzati dalla Game instance
        String[] iaNames = Game.getInstance().getIA();
        // Imposta i nomi dei giocatori
        label1.setText(iaNames[0]);
        label2.setText(iaNames[2]);
        label3.setText(iaNames[1]);
        label4.setText(iaNames[3]);
        // Aggiorna il pannello
        revalidate();
        repaint();
    }
}

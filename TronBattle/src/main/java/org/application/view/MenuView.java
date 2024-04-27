package org.application.view;

import org.application.Model.MenuModel;
import org.application.controller.MenuController;
import org.application.utility.ResourceLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuView extends JPanel {
    // Attributi
    private MenuController controller;
    private MenuModel model;
    private ResourceLoader loader;
    private JPanel panelMenu, panelButton;
    private JButton btnHumanVsIA, btn2Players, btn4Players, btnExit;
    private JLabel titleLabel;
    private Font font;
    // variabili utili per la gestione dell'animazione di sfondo
    private List<BufferedImage> frames;
    private int currentFrame = 0;
    private Timer timer;

    // Costruttore
    public MenuView () {
        // creo il loader
        loader = ResourceLoader.getInstance();

        //setto il cursore personalizzato
        this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(loader.getBufferedImage("/cursor/pointer.png", 32, 32, false), new Point(20, 20), "Cursor"));

        // Carico il font personalizzato
        font = loader.getFont("/font/batmfa.ttf", 28, Font.PLAIN);

        // creo model e controller
        model = new MenuModel(this);
        controller = new MenuController(model, this);

        //Setto il layout del pannello principale
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Inizializzo il menù
        initMenu();

        initImageAnimation();
    }

    // Metodi
    private void initMenu() {
        // Creo il menu
        initComponent();    // qua creo tutti i componenti del menu e li metto nel panelMenu
        controller.addListener();
        this.add(panelMenu);    // aggiungo il panelMenu al pannello principale
        repaint();
        revalidate();
    }
    private void initComponent() {
        // Inizializzo i vari componenti che poi userò nel menù
        btnHumanVsIA = new JButton("Giocatore Singolo");
        btnHumanVsIA.setIcon(new ImageIcon(loader.getBufferedImage("/button/buttonBG.png", 300, 75, false)));
        btnHumanVsIA.setHorizontalTextPosition(JButton.CENTER);
        btnHumanVsIA.setVerticalTextPosition(JButton.CENTER);
        btnHumanVsIA.setFont(font.deriveFont(Font.PLAIN, 20));
        btnHumanVsIA.setBorderPainted(false);
        btnHumanVsIA.setFocusPainted(false);
        btnHumanVsIA.setContentAreaFilled(false);
        btnHumanVsIA.setForeground(Color.WHITE);

        btn2Players = new JButton("IA VS IA");
        btn2Players.setIcon(new ImageIcon(loader.getBufferedImage("/button/buttonBG.png", 300, 75, false)));
        btn2Players.setHorizontalTextPosition(JButton.CENTER);
        btn2Players.setVerticalTextPosition(JButton.CENTER);
        btn2Players.setFont(font.deriveFont(Font.PLAIN, 25));
        btn2Players.setBorderPainted(false);
        btn2Players.setFocusPainted(false);
        btn2Players.setContentAreaFilled(false);
        btn2Players.setForeground(Color.WHITE);

        btn4Players = new JButton("Competition");
        btn4Players.setIcon(new ImageIcon(loader.getBufferedImage("/button/buttonBG.png", 300, 75, false)));
        btn4Players.setHorizontalTextPosition(JButton.CENTER);
        btn4Players.setVerticalTextPosition(JButton.CENTER);
        btn4Players.setFont(font.deriveFont(Font.PLAIN, 25));
        btn4Players.setBorderPainted(false);
        btn4Players.setFocusPainted(false);
        btn4Players.setContentAreaFilled(false);
        btn4Players.setForeground(Color.WHITE);

        btnExit = new JButton("Esci");
        btnExit.setIcon(new ImageIcon(loader.getBufferedImage("/button/buttonBG.png", 300, 75, false)));
        btnExit.setHorizontalTextPosition(JButton.CENTER);
        btnExit.setVerticalTextPosition(JButton.CENTER);
        btnExit.setFont(font.deriveFont(Font.PLAIN, 25));
        btnExit.setBorderPainted(false);
        btnExit.setFocusPainted(false);
        btnExit.setContentAreaFilled(false);
        btnExit.setForeground(Color.WHITE);

        titleLabel = new JLabel();
        ImageIcon image = new ImageIcon(loader.getBufferedImage("/title/TronBattleTitle.png", 604, 85, false));
        titleLabel.setIcon(image);

        // Aggiungo i vari componenti al pannello
        panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setOpaque(false);

        panelButton = new JPanel();
        panelButton.setOpaque(false);
        panelButton.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.CENTER;
        c.insets = new Insets(20, 0, 0, 0);

        // Aggiungo il titolo alla cella 0,0
        c.gridx = 0; c.gridy = 0;
        panelButton.add(titleLabel, c);

        // Aggiungo il bottone "Giocatore Singolo" alla cella 0,1
        c.gridx = 0; c.gridy = 1;
        panelButton.add(btnHumanVsIA, c);

        // Aggiungo il bottone "IA VS IA" alla cella 0,2
        c.gridx = 0; c.gridy = 2;
        panelButton.add(btn2Players, c);

        // Aggiungo il bottone "Competition" alla cella 0,3
        c.gridx = 0; c.gridy = 3;
        panelButton.add(btn4Players, c);

        // Aggiungo il bottone "Esci" alla cella 0,4
        c.gridx = 0; c.gridy = 4;
        panelButton.add(btnExit, c);

        panelMenu.add(panelButton);
    }
    private void initImageAnimation() {
        frames = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            try {
                // Costruisci il percorso dell'immagine basato sull'indice
                String imagePath = "/background/gifFrame/frame" + i + ".jpg";
                // Carica l'immagine
                BufferedImage frame = ImageIO.read(getClass().getResource(imagePath));

                // Ridimensiona l'immagine se necessario
                double scaleX = 1280.0 / frame.getWidth();
                double scaleY = 640.0 / frame.getHeight();
                AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
                AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);
                BufferedImage resizedFrame = bilinearScaleOp.filter(frame, null);

                frames.add(resizedFrame);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        // Inizializzo il timer per l'animazione
        timer = new Timer(70, e -> {
            currentFrame = (currentFrame + 1) % frames.size();
            invalidate();
            repaint();
        });
        timer.start();
    }

    // Override del metodo paintComponent per disegnare l'immagine di sfondo
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (frames != null && !frames.isEmpty()) {
            g.drawImage(frames.get(currentFrame), 0, 0, this);
        }
    }

    // Getters
    public JButton getBtnHumanVsIA() {
        return btnHumanVsIA;
    }
    public JButton getBtn2Players() {
        return btn2Players;
    }
    public JButton getBtn4Players() {
        return btn4Players;
    }
    public JButton getBtnExit() {
        return btnExit;
    }
}

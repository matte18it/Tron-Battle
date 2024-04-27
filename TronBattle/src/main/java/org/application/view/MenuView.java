package org.application.view;

import org.application.Model.MenuModel;
import org.application.controller.MenuController;
import org.application.utility.ResourceLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuView extends JPanel {
    // Attributi
    private MenuController controller;
    private MenuModel model;
    private ResourceLoader loader;
    private JPanel panelMenu, panelButton;
    private JButton btnHumanVsIA, btn2Players, btn4Players, btnExit;
    private JLabel titleLabel;
    private Font font;

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
        btnHumanVsIA.setFont(font);
        btn2Players = new JButton("IA VS IA");
        btn2Players.setFont(font);
        btn4Players = new JButton("Competition");
        btn4Players.setFont(font);
        btnExit = new JButton("Esci");
        btnExit.setFont(font);

        titleLabel = new JLabel();
        ImageIcon image = new ImageIcon(loader.getBufferedImage("/title/TronBattleTitle.png", 604, 85, false));
        titleLabel.setIcon(image);

        // Aggiungo i vari componenti al pannello
        panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));

        panelButton = new JPanel();
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

        // Aggiungo uno spazio vuoto alla cella 0,5
        c.gridx = 0; c.gridy = 5;
        panelButton.add(Box.createVerticalStrut(100), c);

        panelMenu.add(panelButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
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

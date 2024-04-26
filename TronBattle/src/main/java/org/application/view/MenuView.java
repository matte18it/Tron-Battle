package org.application.view;

import org.application.Model.MenuModel;
import org.application.controller.MenuController;
import org.application.utility.ResourceLoader;

import javax.swing.*;
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
        panelMenu.add(Box.createVerticalStrut(70));
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));

        panelButton = new JPanel();
        panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.Y_AXIS));
        panelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelButton.add(titleLabel);
        panelButton.add(btnHumanVsIA);
        panelButton.add(btn2Players);
        panelButton.add(btn4Players);
        panelButton.add(btnExit);

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

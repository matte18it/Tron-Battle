package org.application.controller;

import org.application.model.MenuModel;
import org.application.view.GameFrame;
import org.application.view.MenuView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuController {
    // Attributi
    private MenuModel model;
    private MenuView view;

    // Costruttore
    public MenuController(MenuModel model, MenuView view) {
        this.model = model;
        this.view = view;
    }

    // Metodi
    public void addListener() {
        // Listener per il bottone exit, alla pressione del bottone esce dal gioco
        view.getBtnExit().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                view.stopAnimation();
                System.exit(0);
            }
        });

        // Listener per il bottone 4Players, alla pressione del bottone lancia la competitionm
        view.getBtn4Players().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                view.stopAnimation();
                GameFrame.launchGame();
            }
        });

        // Listener per il bottone Single Player, alla pressione del bottone lancia la modalità Single Player
        view.getBtnHumanVsIA().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                view.stopAnimation();
                GameFrame.singlePlayer();
            }
        });

        // Listener per il bottone 2Players, alla pressione del bottone lancia la modalità 2 Players
        view.getBtn2Players().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                view.stopAnimation();
                GameFrame.twoPlayer();
            }
        });
    }
}

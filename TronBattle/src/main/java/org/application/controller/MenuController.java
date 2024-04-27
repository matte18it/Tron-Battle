package org.application.controller;

import com.github.pervoj.jiconfont.FontAwesomeSolid;
import jiconfont.swing.IconFontSwing;
import org.application.model.MenuModel;
import org.application.utility.Settings;
import org.application.view.GameFrame;
import org.application.view.MenuView;

import javax.swing.*;
import java.awt.*;
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
                chooseSinglePlayerIA();
                view.stopAnimation();
                GameFrame.singlePlayer();
            }
        });

        // Listener per il bottone 2Players, alla pressione del bottone lancia la modalità 2 Players
        view.getBtn2Players().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                chooseTwoPlayerIA();
                view.stopAnimation();
                GameFrame.twoPlayer();
            }
        });
    }

    public void chooseSinglePlayerIA() {
        IconFontSwing.register(FontAwesomeSolid.getIconFont());
        Icon icon = IconFontSwing.buildIcon(FontAwesomeSolid.ROBOT, 40, new Color(82, 135, 172));

        int choice = JOptionPane.showOptionDialog(view, "Contro quale IA vuoi giocare?", "Modalità Giocatore Singolo", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, new String[]{"Palkia", "Dialga", "4F", "NonPiuSoli"}, null);

        switch (choice) {
            case 0:
                Settings.SinglePlayerIA = "Palkia";
                break;
            case 1:
                Settings.SinglePlayerIA = "Dialga";
                break;
            case 2:
                Settings.SinglePlayerIA = "_4F";
                break;
            case 3:
                Settings.SinglePlayerIA = "NonPiuSoli";
                break;
            default:
                String[] choose = {"Palkia", "Dialga", "_4F", "NonPiuSolii"};
                Settings.SinglePlayerIA = choose[(int) (Math.random() * choose.length)];
                break;
        }
    }

    public void chooseTwoPlayerIA() {}
}

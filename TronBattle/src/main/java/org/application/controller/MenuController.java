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
import java.util.Objects;

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
                if(chooseSinglePlayerIA()){
                    view.stopAnimation();
                    GameFrame.singlePlayer();
                }
            }
        });

        // Listener per il bottone 2Players, alla pressione del bottone lancia la modalità 2 Players
        view.getBtn2Players().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(chooseTwoPlayerIA()){
                    view.stopAnimation();
                    GameFrame.twoPlayer();
                }
            }
        });
    }   // aggiunge i listener ai bottoni
    public boolean chooseSinglePlayerIA() {
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
        }

        if(choice == -1)
            return false;

        return true;
    }   // permette di scegliere l'IA con cui giocare
    public boolean chooseTwoPlayerIA() {
        // Registra l'icona
        IconFontSwing.register(FontAwesomeSolid.getIconFont());
        Icon icon = IconFontSwing.buildIcon(FontAwesomeSolid.ROBOT, 40, new Color(82, 135, 172));

        // Array di opzioni per entrambe le scelte
        String[] options = {"Palkia", "Dialga", "4F", "NonPiuSoli"};

        // Array di etichette per le liste a discesa
        String[] labels = {"Prima IA:", "Seconda IA:"};

        // Array di valori per memorizzare le selezioni
        String[] selectedOptions = new String[2];

        // Finestra di dialogo personalizzata con due liste a discesa
        JPanel panel = new JPanel(new GridLayout(0, 2));

        // Aggiungi la label "Scegli le due IA:"
        JLabel chooseLabel = new JLabel("Scegli le due IA:");
        panel.add(chooseLabel);

        // Aggiungi un componente vuoto per allineare le JComboBox
        panel.add(new JPanel());

        // Aggiungi le JComboBox per la selezione delle IA
        JComboBox<String> comboBox1 = new JComboBox<>(options);
        JComboBox<String> comboBox2 = new JComboBox<>(options);
        panel.add(new JLabel(labels[0]));
        panel.add(comboBox1);
        panel.add(new JLabel(labels[1]));
        panel.add(comboBox2);

        // Mostra la finestra di dialogo
        int result = JOptionPane.showConfirmDialog(view, panel, "Modalità IA VS IA", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
        if (result == JOptionPane.OK_OPTION) {
            // Ottieni le selezioni dalle liste a discesa
            selectedOptions[0] = Objects.requireNonNull(comboBox1.getSelectedItem()).toString();
            selectedOptions[1] = Objects.requireNonNull(comboBox2.getSelectedItem()).toString();
        } else {
            return false;
        }

        // se le due IA sono uguali, cambio la seconda IA
        while (selectedOptions[0].equals(selectedOptions[1])) {
            selectedOptions[1] = options[(int) (Math.random() * options.length)];
        }

        // Memorizza le selezioni
        Settings.TwoPlayer_FirstIA = selectedOptions[0];
        Settings.TwoPlayer_SecondIA = selectedOptions[1];

        return true;
    }
}

package org.application.view;

import javax.swing.*;

public class GameFrame extends JPanel {
    public static void launch() {
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 660);
        frame.setVisible(true);
    }
}

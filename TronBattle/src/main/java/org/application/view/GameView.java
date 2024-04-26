package org.application.view;

import org.application.controller.MovementController;

import javax.swing.*;

public class GameView extends JPanel {
    public void setController(MovementController controller) {
        addKeyListener(controller);
    }

}

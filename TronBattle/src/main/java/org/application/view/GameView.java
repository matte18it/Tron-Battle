package org.application.view;

import org.application.controller.MovementController;
import org.application.model.Game;
import org.application.utility.Settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class GameView extends JPanel {
    private Image cella;
    private final String PATH = "/background";
    public void setController(MovementController controller) {
        addKeyListener(controller);
    }
    public GameView(){
        try {
            cella = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(PATH + "/cell.png")));
            cella = cella.getScaledInstance(Settings.CELL_SIZEX, Settings.CELL_SIZEY, Image.SCALE_SMOOTH);
        } catch(IOException e) {
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var game = Game.getInstance();
        // Disegna lo sfondo per ogni cella
        for(int i = 0; i < game.getBlocks().length; i++) {
            int x = Settings.CELL_SIZEX * i;
            for(int j = 0; j < game.getBlocks()[i].length; j++) {
                int y = Settings.CELL_SIZEY * j;
                g.drawImage(cella, x, y, Settings.CELL_SIZEX, Settings.CELL_SIZEY, this); // Disegna l'immagine di sfondo per ogni cella
            }
        }



    }


}

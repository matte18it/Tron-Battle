package org.application.view;

import org.application.controller.MovementController;
import org.application.model.Game;
import org.application.utility.ResourceLoader;
import org.application.utility.Settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class CompetitionView extends JPanel {
    private final Image cella;
    private final Image[][] giocatori = new Image[4][4];
    public void setController(MovementController controller) {
        addKeyListener(controller);
    }
    public CompetitionView() {
        cella = ResourceLoader.getInstance().getBufferedImage("/background/cell.png", Settings.CELL_SIZEX, Settings.CELL_SIZEY, false);
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case Settings.RIGHT:
                        giocatori[j][i] = ResourceLoader.getInstance().getBufferedImage("/player" + (j+1) + "/car_right.png", Settings.CELL_SIZEX, Settings.CELL_SIZEY, false);
                        break;
                    case Settings.LEFT:
                        giocatori[j][i] = ResourceLoader.getInstance().getBufferedImage("/player" + (j+1) + "/car_left.png", Settings.CELL_SIZEX, Settings.CELL_SIZEY, false);
                        break;
                    case Settings.UP:
                        giocatori[j][i] = ResourceLoader.getInstance().getBufferedImage("/player" + (j+1) + "/car_up.png", Settings.CELL_SIZEX, Settings.CELL_SIZEY, false);
                        break;
                    case Settings.DOWN:
                        giocatori[j][i] = ResourceLoader.getInstance().getBufferedImage("/player" + (j+1) + "/car_down.png", Settings.CELL_SIZEX, Settings.CELL_SIZEY, false);
                        break;
                }
            }
    }}
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

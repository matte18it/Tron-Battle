package org.application.view;

import org.application.controller.MovementController;
import org.application.model.Game;
import org.application.utility.ResourceLoader;
import org.application.utility.Settings;

import javax.swing.*;
import java.awt.*;

import org.application.model.Block;

public class GameView extends JPanel {
    private final Image cella;
    private final Image[][] giocatori = new Image[4][4];
    public void setController(MovementController controller) {
        addKeyListener(controller);
    }
    public GameView() {
        cella = ResourceLoader.getInstance().getBufferedImage("/background/cell.png", Settings.CELL_SIZEX, Settings.CELL_SIZEY, false);
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case Settings.RIGHT:
                        giocatori[j][i] = ResourceLoader.getInstance().getBufferedImage("/player" + (j+1) + "/car_right.png", Settings.PLAYER_SIZEX, Settings.PLAYER_SIZEY, false);
                        break;
                    case Settings.LEFT:
                        giocatori[j][i] = ResourceLoader.getInstance().getBufferedImage("/player" + (j+1) + "/car_left.png", Settings.PLAYER_SIZEX, Settings.PLAYER_SIZEY, false);
                        break;
                    case Settings.UP:
                        giocatori[j][i] = ResourceLoader.getInstance().getBufferedImage("/player" + (j+1) + "/car_up.png", Settings.PLAYER_SIZEX, Settings.PLAYER_SIZEY, false);
                        break;
                    case Settings.DOWN:
                        giocatori[j][i] = ResourceLoader.getInstance().getBufferedImage("/player" + (j+1) + "/car_down.png", Settings.PLAYER_SIZEX, Settings.PLAYER_SIZEY, false);
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
                g.drawImage(cella, x, y, null);
            }
        }
        for(int i = 0; i < game.getBlocks().length; i++) {
            int x = Settings.CELL_SIZEX * i;
            for(int j = 0; j < game.getBlocks()[i].length; j++) {
                int y = Settings.CELL_SIZEY * j;
                switch (game.getBlocks()[i][j].type()){
                    case Block.PLAYER1_HEAD -> {
                        switch (game.getDirectionPlayer1()) {
                            case Settings.RIGHT -> g.drawImage(giocatori[0][Settings.RIGHT], x-12, y-15, null);
                            case Settings.LEFT -> g.drawImage(giocatori[0][Settings.LEFT], x-20, y-15, null);
                            case Settings.UP -> g.drawImage(giocatori[0][Settings.UP], x-15, y-15, null);
                            case Settings.DOWN -> g.drawImage(giocatori[0][Settings.DOWN], x-15, y-15, null);
                        }}
                    case Block.PLAYER2_HEAD -> {
                        switch (game.getDirectionPlayer2()) {
                            case Settings.RIGHT -> g.drawImage(giocatori[1][Settings.RIGHT], x-12, y-15, null);
                            case Settings.LEFT -> g.drawImage(giocatori[1][Settings.LEFT], x-20, y-15, null);
                            case Settings.UP -> g.drawImage(giocatori[1][Settings.UP], x-15, y-15, null);
                            case Settings.DOWN -> g.drawImage(giocatori[1][Settings.DOWN], x-15, y-15, null);
                        }
                    }
                    case Block.PLAYER3_HEAD -> {
                        switch (game.getDirectionPlayer3()) {
                            case Settings.RIGHT -> g.drawImage(giocatori[2][Settings.RIGHT], x-12, y-15, null);
                            case Settings.LEFT -> g.drawImage(giocatori[2][Settings.LEFT], x-20, y-15, null);
                            case Settings.UP -> g.drawImage(giocatori[2][Settings.UP], x-15, y-15, null);
                            case Settings.DOWN -> g.drawImage(giocatori[2][Settings.DOWN], x-15, y-15, null);
                    }}
                    case Block.PLAYER4_HEAD -> {
                        switch (game.getDirectionPlayer4()){
                        case Settings.RIGHT -> g.drawImage(giocatori[3][Settings.RIGHT], x-12, y-15, null);
                        case Settings.LEFT -> g.drawImage(giocatori[3][Settings.LEFT], x-20, y-15, null);
                        case Settings.UP -> g.drawImage(giocatori[3][Settings.UP], x-15, y-15, null);
                        case Settings.DOWN -> g.drawImage(giocatori[3][Settings.DOWN], x-15, y-15, null);
                    }}
                    case Block.PLAYER1_BODY -> {g.setColor(Color.GREEN);g.fillRect(x, y, Settings.CELL_SIZEX, Settings.CELL_SIZEY);}
                    case Block.PLAYER2_BODY -> {g.setColor(Color.WHITE);g.fillRect(x, y, Settings.CELL_SIZEX, Settings.CELL_SIZEY);}
                    case Block.PLAYER3_BODY -> {g.setColor(Color.ORANGE);g.fillRect(x, y, Settings.CELL_SIZEX, Settings.CELL_SIZEY);}
                    case Block.PLAYER4_BODY -> {g.setColor(Color.BLUE);g.fillRect(x, y, Settings.CELL_SIZEX, Settings.CELL_SIZEY);}

                }
            }
        }
    }
}

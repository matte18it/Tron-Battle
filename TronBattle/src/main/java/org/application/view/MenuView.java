package org.application.view;

import org.application.Model.MenuModel;
import org.application.controller.MenuController;
import org.application.utility.ResourceLoader;

import java.awt.*;

public class MenuView extends Panel {
    // Attributi
    private MenuController controller;
    private MenuModel model;
    private ResourceLoader loader = ResourceLoader.getInstance();

    // Costruttore
    public MenuView () {
        //setto il cursore personalizzato
        this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(loader.getBufferedImage("/cursor/pointer.png", 32, 32, false), new Point(20, 20), "Cursor"));

        // creo model e controller
        model = new MenuModel(this);
        controller = new MenuController(model);
        this.setBackground(Color.red);
    }

    // Metodi

}

package org.application.IA.IA_Palkia.Utility;

import lombok.Data;
import org.application.IA.IA_Palkia.Research.Support.Cell;

import java.util.ArrayList;
import java.util.List;

@Data
public class DataClass {
    // ----- VARIABLES -----
    private static DataClass instance = null;   // istanza della classe
    private int firstMove = 0;  // mossa iniziale dell'IA
    private int eyeVision = 5;  // visione dell'IA
    private Cell enemyCoordinates = null; // coordinate dell'avversario più vicino
    private int enemyId = 0;    // id dell'avversario più vicino
    private List<Cell> nextCell = new ArrayList<>();   // lista che contiene le mosse successive che l'IA può fare in qualsiasi momento
    private List<Cell> minStreet = new ArrayList<>();  // lista che contiene la strada minima da fare
    private int playerX = 0, playerY = 0;   // coordinate del player
    private List<Cell> pathChiusura = new ArrayList<>();  // lista che contiene la strada per chiudere l'avversario
    private int fixedDistance = 20; // distanza a cui vede il nemico
    private int enemyNear = 15; // distanza a cui l'IA considera un nemico vicino

    // ----- CONSTRUCTOR -----
    private DataClass() {}
    public static DataClass getInstance() {
        if (instance == null) {
            instance = new DataClass();
        }
        return instance;
    }

    // ----- METHODS -----
    public void reset() {
        firstMove = 0;
        eyeVision = 5;
        enemyCoordinates = null;
        nextCell.clear();
        minStreet.clear();
        playerX = 0;
        playerY = 0;
        pathChiusura.clear();
        fixedDistance = 20;
        enemyId = 0;
        enemyNear = 15;
    }
}

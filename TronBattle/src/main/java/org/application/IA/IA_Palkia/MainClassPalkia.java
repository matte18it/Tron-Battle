package org.application.IA.IA_Palkia;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.platforms.desktop.DesktopService;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import org.application.IA.IA_Palkia.Facts.*;
import org.application.IA.IA_Palkia.Research.AStar.AStar;
import org.application.IA.IA_Palkia.Research.BFS.BreadthFirstSearch;
import org.application.IA.IA_Palkia.Research.Support.Cell;
import org.application.IA.IA_Palkia.Utility.CheckClass;
import org.application.IA.IA_Palkia.Utility.DataClass;
import org.application.IA.IA_Palkia.Utility.FindLockingPath;
import org.application.model.Block;
import org.application.utility.Settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainClassPalkia {
    // ----- ATTRIBUTE -----
    private static MainClassPalkia instance = null; // istanza della classe
    private final int[][] directions = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};  // destra, sinistra, su, giù
    private Random random = new Random();   // istanza di random per la scelta casuale

    // Attributi di configurazione per EMBASP
    private DesktopService service;
    private Handler handler;
    private OptionDescriptor option;
    private InputProgram fixedProgram;
    private InputProgram variableProgram;
    private Output output;
    private AnswerSets answersets;

    // ----- CONSTRUCTOR -----
    private MainClassPalkia() {}
    public static MainClassPalkia getInstance() {
        // funzione per ottenere l'istanza del MainClassPalkia
        if(instance == null)
            instance = new MainClassPalkia();

        return instance;
    }

    // ----- INIT METHODS -----
    public void init() {
        //linux
        service = new DLV2DesktopService("lib/dlv-2");
        //win
        //service = new DLV2DesktopService("lib/dlv2.exe");
        handler = new DesktopHandler(service);
        fixedProgram = new ASPInputProgram();
        fixedProgram.addFilesPath("encodings/encodings_Palkia/findBestCellForPath.txt");
        variableProgram = new ASPInputProgram();
        variableProgram.addFilesPath("encodings/encodings_Palkia/predicateProgram.txt");
        handler.addProgram(fixedProgram);
        handler.addProgram(variableProgram);
        registerClass();
    }
    private void registerClass() {
        // Registro le classi per l'uso di EMBASP
        try{
            ASPMapper.getInstance().registerClass(IndirectCellDepth.class);
            ASPMapper.getInstance().registerClass(IndirectCell.class);
            ASPMapper.getInstance().registerClass(MinDistance.class);
            ASPMapper.getInstance().registerClass(NextCell.class);
            ASPMapper.getInstance().registerClass(FinalMove.class);
            ASPMapper.getInstance().registerClass(FreeAdjacent.class);
            ASPMapper.getInstance().registerClass(EnemyDistanceFromNextCell.class);
            ASPMapper.getInstance().registerClass(DistanceBetweenEnemies.class);
            ASPMapper.getInstance().registerClass(DepthFreeSpace.class);
            ASPMapper.getInstance().registerClass(FinalEnemy.class);
            ASPMapper.getInstance().registerClass(DistanceBetweenPlayerEnemy.class);
            ASPMapper.getInstance().registerClass(EdgeDistance.class);
            ASPMapper.getInstance().registerClass(ReachableEnemy.class);
        }
        catch (Exception e) {
            System.out.println("Error in registerClass: " + e.getMessage());
        }
    }

    // ----- MAIN METHODS -----
    public int getDirection(Block[][] blocks, int playerHead, int playerBody) {
        // verifico se sono all'inizio della partita (se non ci sono muri sulla mappa)
        if(CheckClass.wallNumber(blocks) == 0)
            DataClass.getInstance().reset();

        // pulisco i fatti
        variableProgram.clearAll();

        // analizzo la matrice e verifico se il player è ancora vivo, sennò salto il tutto
        try {
            analyzeMatrix(blocks, playerHead);  // analizzo la matrice e prendo la posizione del player
            // Parto ottimizzando lo spazio per chiudere le celle sopra di me
            if(DataClass.getInstance().getFirstMove() < 3){
                DataClass.getInstance().setFirstMove(DataClass.getInstance().getFirstMove() + 1);
                return closedOptimizationStrategy(blocks);
            }

            // ----- GAME STRATEGY -----
            // Inizio verificando se sono aperto o chiuso
            if(CheckClass.isPlayerEnclosed(blocks, playerHead)){
                // se entro qua vuol dire che il player si trova in un ambiente chiuso, quindi ottimizzo
                return closedOptimizationStrategy(blocks);
            }
            else {
                if(!DataClass.getInstance().getPathChiusura().isEmpty()){
                    FinalMove move = new FinalMove(DataClass.getInstance().getPathChiusura().get(0).x, DataClass.getInstance().getPathChiusura().get(0).y);
                    DataClass.getInstance().getPathChiusura().remove(0);
                    return analyzeDirection(move);
                }
                else{
                    // se entro qua vuol dire che mi trovo in un ambiente aperto
                    int findEnemyDistance = findEnemyDistance(blocks, playerHead);
                    variableProgram.clearAll(); // pulisco i fatti
                    if(findEnemyDistance == -1) {
                        // se mi restituisce -1 vuol dire che ASP non mi ha restituito nulla oppure che non ho nemici raggiungibili
                        return closedOptimizationStrategy(blocks);
                    }
                    else {
                        // se entro qua vuol dire che ho trovato il nemico e voglio attaccare
                        if(findEnemyDistance < DataClass.getInstance().getFixedDistance()){
                            if(FindLockingPath.getInstance().evaluate(blocks, playerHead)) {
                                // se sono qua, vuol dire ho il path di chiusura, per cui mi muovo lungo il path
                                FinalMove move = new FinalMove(DataClass.getInstance().getPathChiusura().get(0).x, DataClass.getInstance().getPathChiusura().get(0).y);
                                DataClass.getInstance().getPathChiusura().remove(0);
                                return analyzeDirection(move);
                            }
                            else {
                                // se entro qua vuol dire che non ho il path di chiusura
                                return setAttackStrategy(blocks);
                            }
                        }
                        else {
                            return findPath(blocks, playerHead);
                        }
                    }
                }
            }
        }
        catch (Exception e) { System.out.println("Error in GetDirection: " + e.getMessage()); }

        return 0;
    }
    private void analyzeMatrix(Block[][] blocks, int playerHead) throws Exception {
        // inizio a scorrere la matrice per costruire tutti i fatti da dare all'IA
        for (int i = 0; i < blocks.length; i++){
            for (int j = 0; j < blocks[i].length; j++) {
                boolean hasAdjacentZero = (i > 0 && blocks[i-1][j].type() == 0) || (i < blocks.length - 1 && blocks[i+1][j].type() == 0) || (j > 0 && blocks[i][j-1].type() == 0) || (j < blocks[i].length - 1 && blocks[i][j+1].type() == 0);
                if((blocks[i][j].type() == playerHead) && hasAdjacentZero) {
                    DataClass.getInstance().setPlayerX(i);
                    DataClass.getInstance().setPlayerY(j); // prendo le coordinate del player
                }
            }
        }
    }
    private int analyzeDirection(FinalMove move) {
        // analizza la mossa e restituisce la direzione
        if (move.getX() > DataClass.getInstance().getPlayerX()) {
            return 0; // Right
        } else if (move.getX() < DataClass.getInstance().getPlayerX()) {
            return 1; // Left
        } else if (move.getY() < DataClass.getInstance().getPlayerY()) {
            return 2; // Up
        } else if (move.getY() > DataClass.getInstance().getPlayerY()) {
            return 3; // Down
        }
        return -1; // No movement or undefined
    }

    // ----- MODULE METHODS -----
    private int closedOptimizationStrategy(Block[][] blocks) throws Exception {
        // Modulo per la strategia di ottimizzazione dello spazio in ambienti aperti
        DataClass.getInstance().setEyeVision(2);    // setto eye vision

        // qua setto il modulo di ottimizzazione per l'IA (in spazi chiusi): il metodo di ottimizzazione ottimizza lo spazio con l'obiettivo di far rimanere la IA in vita il più a lungo possibile
        if(!fixedProgram.getStringOfFilesPaths().equals("encodings/encodings_Palkia/optimizationStrategy.txt ")){
            fixedProgram.clearFilesPaths();
            fixedProgram.addFilesPath("encodings/encodings_Palkia/optimizationStrategy.txt");
        }

        DataClass.getInstance().getNextCell().clear();  // pulisco la lista delle celle adiacenti libere

        // mi costruisco i fatti che mi servono per questo modulo
        nextCell(blocks);   // calcolo le celle adiacenti libere
        countAdjacentFreeCells(blocks); // calcolo le celle adiacenti libere per ogni cella presente in nextCell
        countAdjacentfreeCellsDepth(blocks);  // calcolo per ogni cella raggiungibile nella mia area di visione il mumero di celle adiacenti libere
        countEdgeDistance(blocks);  // calcolo la distanza dal bordo per ogni nextcell
        for(Cell cell : DataClass.getInstance().getNextCell())
            variableProgram.addObjectInput(new IndirectCell(cell.x, cell.y, BreadthFirstSearch.getInstance().indirectCell(blocks, new Cell(cell.x, cell.y, 0))));

        FinalMove elements = runProgram(FinalMove.class);
        return analyzeDirection(elements);
    }
    private int setAttackStrategy(Block[][] blocks) throws Exception {
        // Modulo per la strategia di attacco
        DataClass.getInstance().setEyeVision(5);    // setto eye vision

        // setto il modulo di attacco
        if(!fixedProgram.getStringOfFilesPaths().equals("encodings/encodings_Palkia/attackStrategy.txt " )){
            fixedProgram.clearFilesPaths();
            fixedProgram.addFilesPath("encodings/encodings_Palkia/attackStrategy.txt");
        }

        // !!! CALCOLO I FATTI ASP !!!
        nextCell(blocks);   // calcolo le celle adiacenti libere
        for(Cell cell : DataClass.getInstance().getNextCell()) {
            // 1) Calcolo per ogni nextCell le celle indirettamente collegate
            variableProgram.addObjectInput(new IndirectCell(cell.x, cell.y, BreadthFirstSearch.getInstance().indirectCell(blocks, new Cell(cell.x, cell.y, 0))));
            // 2) Calcolo per ogni nextCell la cella che lo tiene più lontano dal nemico più vicino
            variableProgram.addObjectInput(new MinDistance(cell.x, cell.y, BreadthFirstSearch.getInstance().searchStartEnd(blocks, new Cell(cell.x, cell.y, 0), new Cell(DataClass.getInstance().getEnemyCoordinates().x, DataClass.getInstance().getEnemyCoordinates().y, 0))));
            // 3) Vai verso la cella con più spazio adiacente
            variableProgram.addObjectInput(new IndirectCellDepth(cell.x, cell.y, BreadthFirstSearch.getInstance().indirectCell(blocks, new Cell(cell.x, cell.y, 0), 2)));
        }

        // Runno il programma e prendo la mossa
        FinalMove elements = runProgram(FinalMove.class);
        return analyzeDirection(elements);
    }
    private int findPath(Block[][] blocks, int playerHead) throws Exception {
        // Modulo per determinare il path verso il nemico
        DataClass.getInstance().setEyeVision(5);    // setto eye vision

        // setto il modulo di espansione se non è già settato
        if(!fixedProgram.getStringOfFilesPaths().equals("encodings/encodings_Palkia/findBestCellForPath.txt " )){
            fixedProgram.clearFilesPaths();
            fixedProgram.addFilesPath("encodings/encodings_Palkia/findBestCellForPath.txt");
            DataClass.getInstance().getMinStreet().clear();
        }

        // se non ho ancora trovato la strada minima, la cerco
        if(DataClass.getInstance().getMinStreet().isEmpty() || !CheckClass.isRouteValid(blocks, playerHead) || !CheckClass.isAdjacent(new Cell(DataClass.getInstance().getMinStreet().get(0).x, DataClass.getInstance().getMinStreet().get(0).y, 0))){
            if(!DataClass.getInstance().getMinStreet().isEmpty())
                DataClass.getInstance().getMinStreet().clear();

            nextNCell(blocks, playerHead);  // calcolo le celle che vede il player a distanza N e che sono vicino al bordo

            //System.out.println(fixedProgram.getPrograms());
            FinalMove elements = runProgram(FinalMove.class);

            //calcolo il cammino
            DataClass.getInstance().getMinStreet().addAll(Objects.requireNonNull(AStar.findPath(blocks, new Cell(DataClass.getInstance().getPlayerX(), DataClass.getInstance().getPlayerY(), 0), new Cell(elements.getX(), elements.getY(), 0) ,-1)));
            DataClass.getInstance().getMinStreet().remove(0);
        }
        FinalMove move = new FinalMove(DataClass.getInstance().getMinStreet().get(0).x, DataClass.getInstance().getMinStreet().get(0).y);
        DataClass.getInstance().getMinStreet().remove(0);
        return analyzeDirection(move);
    }

    // ----- RUNNING METHOD -----
    private<T> T runProgram(Class<T> type) {
        // esegue il programma e restituisce gli answer set
        output = handler.startSync();   // eseguo il programma
        answersets = (AnswerSets) output;   // prendo gli answerSets
        ArrayList<T> elements = new ArrayList<>();  // lista di elementi di tipo generico
        for (AnswerSet answerSet : answersets.getOptimalAnswerSets()) {
            try {
                for (Object obj : answerSet.getAtoms()) {
                    if (type.isInstance(obj)) {
                        elements.add(type.cast(obj));
                    }
                }
            } catch (Exception e) {
                System.out.println("Error in runProgram: " + e.getMessage());
            }
        }   // prendo le mosse finali

        return elements.get(random.nextInt(elements.size()));
    }

    // ----- OTHER METHODS -----
    private void nextCell(Block[][] blocks) throws Exception {
        // controllo se le celle adiacenti sono libere
        for (int[] direction : directions) {
            int newX = DataClass.getInstance().getPlayerX() + direction[0];
            int newY = DataClass.getInstance().getPlayerY() + direction[1];

            if (newX >= 0 && newX < Settings.WORLD_SIZEX && newY >= 0 && newY < Settings.WORLD_SIZEY) {
                if (blocks[newX][newY].type() == 0) {
                    variableProgram.addObjectInput(new NextCell(newX, newY));   // do alla mia IA le coordinate delle celle adiacenti libere
                    DataClass.getInstance().getNextCell().add(new Cell(newX, newY, 0)); // aggiungo alla lista delle celle adiacenti libere
                }
            }
        }
    }
    private void countAdjacentFreeCells(Block[][] blocks) throws Exception {
        // Conta le celle adiacenti libere per ogni cella presente in nextCell
        for (Cell cell : DataClass.getInstance().getNextCell()) {
            int freeCount = 0;
            int x = cell.x;
            int y = cell.y;

            // Controlla le quattro direzioni: sopra, sotto, a destra, a sinistra
            if (y > 0 && blocks[x][y - 1].type() == 0) freeCount++; // Sinistra
            if (y < blocks[0].length - 1 && blocks[x][y + 1].type() == 0) freeCount++; // Destra
            if (x > 0 && blocks[x - 1][y].type() == 0) freeCount++; // Sopra
            if (x < blocks.length - 1 && blocks[x + 1][y].type() == 0) freeCount++; // Sotto

            variableProgram.addObjectInput(new FreeAdjacent(x, y, freeCount));
        }
    }
    private int findEnemyDistance(Block[][] blocks, int playerHead) throws Exception {
        List<Cell> enemycell = new ArrayList<>();  // qua dentro ho le celle dei nemici
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks[i].length; j++)
                if((blocks[i][j].type() == Block.PLAYER1_HEAD && blocks[i][j].type() != playerHead) || (blocks[i][j].type() == Block.PLAYER2_HEAD && blocks[i][j].type() != playerHead) || (blocks[i][j].type() == Block.PLAYER3_HEAD && blocks[i][j].type() != playerHead) || (blocks[i][j].type() == Block.PLAYER4_HEAD && blocks[i][j].type() != playerHead))
                    enemycell.add(new Cell(i, j, 0));

        // una volta verificato che ci sono nemici, prendo solo i nemici che posso raggiungere
        List<Cell> reachableEnemies = new ArrayList<>();
        for(Cell cell : enemycell){
            int sup = BreadthFirstSearch.getInstance().searchStartEnd(blocks, new Cell(DataClass.getInstance().getPlayerX(), DataClass.getInstance().getPlayerY(), 0), new Cell(cell.x, cell.y, 0));
            if(sup != -1)
                reachableEnemies.add(cell);
        }

        // se non ci sono nemici raggiungibili, restituisco -1
        if(reachableEnemies.isEmpty())
            return -1;

        // se ci sono nemici che posso raggiungere verifico che non siano tutti vicini
        if(reachableEnemies.size() > 1){
            int allEnemiesClose = 0;
            for (int i = 0; i < reachableEnemies.size(); i++) {
                for (int j = i + 1; j < reachableEnemies.size(); j++) {
                    int distance = BreadthFirstSearch.getInstance().searchStartEnd(blocks, reachableEnemies.get(i), reachableEnemies.get(j));
                    if (distance != -1 && distance <= DataClass.getInstance().getEnemyNear())
                        allEnemiesClose++;
                }
            }
            if(reachableEnemies.size() == 3)
                if(allEnemiesClose == reachableEnemies.size())
                    return -1;
            if(reachableEnemies.size() == 2)
                if(allEnemiesClose == reachableEnemies.size() - 1)
                    return -1;
        }

        // !!! Se arrivo qua vuol dire che non sono tutti vicini, quindi ora calcolo i fatti per ASP !!!
        // Passo i nemici ad ASP
        for(Cell cell : reachableEnemies)
            variableProgram.addObjectInput(new ReachableEnemy(cell.x, cell.y));

        // 1) Calcolo la distanza da me ad ogni nemico raggiungibile e la passo ad ASP
        for(Cell cell : reachableEnemies) {
            int supp = BreadthFirstSearch.getInstance().searchStartEnd(blocks, new Cell(DataClass.getInstance().getPlayerX(), DataClass.getInstance().getPlayerY(), 0), new Cell(cell.x, cell.y, 0));
            if(supp != -1)
                variableProgram.addObjectInput(new DistanceBetweenPlayerEnemy(cell.x, cell.y, supp));
        }

        // 2) Calcolo il nemico che ha meno celle adiacenti
        for(Cell cell : reachableEnemies)
            variableProgram.addObjectInput(new DepthFreeSpace(cell.x, cell.y, BreadthFirstSearch.getInstance().indirectCell(blocks, new Cell(cell.x, cell.y, 0), DataClass.getInstance().getEyeVision())));

        // 3) Calcolo il nemico più vicino al bordo in linea d'aria
        countEnemyEdgeDistance(blocks, reachableEnemies);

        // 4) Calcolo la distanza tra ogni nemico
        int sumCell = 0;
        for(Cell cell : reachableEnemies) {
            sumCell = 0;
            for(int i = 0; i < reachableEnemies.size(); i++){
                if(cell.x != reachableEnemies.get(i).x && cell.y != reachableEnemies.get(i).y){
                    int distance = BreadthFirstSearch.getInstance().searchStartEnd(blocks, cell, reachableEnemies.get(i));
                    if(distance != -1)
                        sumCell += distance;
                }
            }
            variableProgram.addObjectInput(new DistanceBetweenEnemies(cell.x, cell.y, sumCell));
        }

        // setto il programma
        fixedProgram.clearFilesPaths();
        fixedProgram.addFilesPath("encodings/encodings_Palkia/chooseEnemy.txt");

        // eseguo il programma e restituisco gli answer set
        FinalEnemy bestEnemy = runProgram(FinalEnemy.class);

        fixedProgram.clearFilesPaths();
        fixedProgram.addFilesPath("encodings/encodings_Palkia/findBestCellForPath.txt");

        if(bestEnemy != null){
            // verifico se è lo stesso che ho trovato precedentemente, se è lo stesso, restituisco la distanza
            if(DataClass.getInstance().getEnemyId() == blocks[bestEnemy.getX()][bestEnemy.getY()].type()) {
                DataClass.getInstance().setEnemyCoordinates(new Cell(bestEnemy.getX(), bestEnemy.getY(), 0));
                int sup = BreadthFirstSearch.getInstance().searchStartEnd(blocks, new Cell(DataClass.getInstance().getPlayerX(), DataClass.getInstance().getPlayerY(), 0), new Cell(bestEnemy.getX(), bestEnemy.getY(), 0));
                return  sup != -1 ? sup : 0;
            } else {
                // se non è lo stesso, allora aggiorno ed elimino il path se l'avevo precedentemente e restituisco la distanza
                DataClass.getInstance().setEnemyCoordinates(new Cell(bestEnemy.getX(), bestEnemy.getY(), 0));
                DataClass.getInstance().setEnemyId(blocks[bestEnemy.getX()][bestEnemy.getY()].type());
                if(!DataClass.getInstance().getMinStreet().isEmpty())
                    DataClass.getInstance().getMinStreet().clear();
                if(!DataClass.getInstance().getPathChiusura().isEmpty())
                    DataClass.getInstance().getPathChiusura().clear();
                int sup = BreadthFirstSearch.getInstance().searchStartEnd(blocks, new Cell(DataClass.getInstance().getPlayerX(), DataClass.getInstance().getPlayerY(), 0), new Cell(bestEnemy.getX(), bestEnemy.getY(),0));
                return sup != -1 ? sup : 0;
            }
        }

        return -1;
    }
    private void nextNCell(Block[][] blocks, int playerHead) throws Exception {
        // SPIEGAZIONE: metodo per calcolare le celle che vede il player a distanza N
        int rows = blocks.length; // Numero di righe
        int cols = blocks[0].length; // Numero di colonne

        // Calcola l'estensione del quadrato di visione attorno alla posizione del giocatore
        int startRow = Math.max(DataClass.getInstance().getPlayerX() - DataClass.getInstance().getEyeVision(), 0); // Inizio delle righe nel raggio di visione
        int endRow = Math.min(DataClass.getInstance().getPlayerX() + DataClass.getInstance().getEyeVision(), rows - 1); // Fine delle righe nel raggio di visione
        int startCol = Math.max(DataClass.getInstance().getPlayerY() - DataClass.getInstance().getEyeVision(), 0); // Inizio delle colonne nel raggio di visione
        int endCol = Math.min(DataClass.getInstance().getPlayerY() + DataClass.getInstance().getEyeVision(), cols - 1); // Fine delle colonne nel raggio di visione

        boolean isAttackMode = fixedProgram.getStringOfFilesPaths().equals("encodings/encodings_Palkia/findBestCellForPath.txt " );

        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                // Evitiamo di processare la cella in cui si trova il giocatore o celle non vuote
                if ((row == DataClass.getInstance().getPlayerX() && col == DataClass.getInstance().getPlayerY()) || blocks[row][col].type() != 0) continue;
                // Evitiamo di processare le celle che non sono raggiungibili
                if(BreadthFirstSearch.getInstance().searchStartEnd(blocks, new Cell(DataClass.getInstance().getPlayerX(), DataClass.getInstance().getPlayerY(), 0), new Cell(row, col, 0)) == -1) continue;
                // Se siamo in modalità di attacco, escludiamo le celle che non sono sulla stessa x o y del giocatore
                if (isAttackMode && row != DataClass.getInstance().getPlayerX() && col != DataClass.getInstance().getPlayerY()) continue;

                variableProgram.addObjectInput(new NextCell(row, col));
                variableProgram.addObjectInput(new IndirectCell(row, col, BreadthFirstSearch.getInstance().indirectCell(blocks, new Cell(row, col, 0))));
                if (isAttackMode) {
                    int sup = BreadthFirstSearch.getInstance().searchStartEnd(blocks, new Cell(DataClass.getInstance().getEnemyCoordinates().x, DataClass.getInstance().getEnemyCoordinates().y, 0), new Cell(row, col, 0));
                    variableProgram.addObjectInput(new EnemyDistanceFromNextCell(row, col, sup != -1 ? sup : 0));
                } else {
                    defineCellWeight(blocks, row, col, playerHead);
                }
                if (isAttackMode) {
                    variableProgram.addObjectInput(new IndirectCellDepth(row, col, BreadthFirstSearch.getInstance().indirectCell(blocks, new Cell(row, col, 0), DataClass.getInstance().getEyeVision())));
                } else {
                    variableProgram.addObjectInput(new IndirectCellDepth(row, col, BreadthFirstSearch.getInstance().indirectCell(blocks, new Cell(row, col, 0), 10)));
                }
            }
        }
    }
    private void defineCellWeight(Block[][] blocks, int row, int col, int playerHead) throws Exception {
        // calcolo la posizione di ogni nemico
        List<Cell> enemyCell = new ArrayList<>();
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks[i].length; j++)
                if((blocks[i][j].type() == Block.PLAYER1_HEAD && blocks[i][j].type() != playerHead) || (blocks[i][j].type() == Block.PLAYER2_HEAD && blocks[i][j].type() != playerHead) || (blocks[i][j].type() == Block.PLAYER3_HEAD && blocks[i][j].type() != playerHead) || (blocks[i][j].type() == Block.PLAYER4_HEAD && blocks[i][j].type() != playerHead))
                    enemyCell.add(new Cell(i, j, 0));

        // calcolo la distanza dalla cella ad ogni nemico
        List<Integer> distance = new ArrayList<>();
        for(Cell cell : enemyCell){
            int sup = BreadthFirstSearch.getInstance().searchStartEnd(blocks, new Cell(cell.x, cell.y, 0), new Cell(row, col, 0));
            distance.add(sup != -1 ? sup : 0);
        }

        // sommo le distanze
        int totalCost = 0;
        for(int i = 0; i < distance.size(); i++)
            totalCost += distance.get(i);

        variableProgram.addObjectInput(new EnemyDistanceFromNextCell(row, col, totalCost));
    }
    private void countAdjacentfreeCellsDepth(Block[][] blocks) throws Exception {
        int rows = blocks.length; // Numero di righe
        int cols = blocks[0].length; // Numero di colonne
        int eyeVision = DataClass.getInstance().getEyeVision();
        int supp = 0;

        // Per ogni cella in nextCell, calcola la distanza da tutte le altre celle nella mappa
        for (Cell nextCell : DataClass.getInstance().getNextCell()) {
            int startX = nextCell.x;
            int startY = nextCell.y;

            // Calcola l'estensione del quadrato di visione attorno alla posizione della cella nextCell
            int startRow = Math.max(startX - eyeVision, 0); // Inizio delle righe nel raggio di visione
            int endRow = Math.min(startX + eyeVision, rows - 1); // Fine delle righe nel raggio di visione
            int startCol = Math.max(startY - eyeVision, 0); // Inizio delle colonne nel raggio di visione
            int endCol = Math.min(startY + eyeVision, cols - 1); // Fine delle colonne nel raggio di visione

            for (int row = startRow; row <= endRow; row++) {
                for (int col = startCol; col <= endCol; col++) {
                    // Evitiamo di processare la cella di partenza o celle non vuote
                    if ((row == startX && col == startY) || blocks[row][col].type() != 0) continue;

                    // Verifica se posso raggiungere la cella
                    int distance = BreadthFirstSearch.getInstance().searchStartEnd(blocks, new Cell(startX, startY, 0), new Cell(row, col, 0));
                    if (distance != -1) {
                        // Calcola il numero di celle adiacenti libere fino a profondità eyeVision
                        int freeCellCount = BreadthFirstSearch.getInstance().indirectCell(blocks, new Cell(row, col, 0), eyeVision);

                        supp = distance + freeCellCount;
                        variableProgram.addObjectInput(new IndirectCellDepth(startX, startY, supp));
                    }
                }
            }
        }
    }
    private void countEdgeDistance(Block[][] blocks) throws Exception {
        // Ottieni le dimensioni della mappa
        int rows = blocks.length;
        int cols = blocks[0].length;

        // Per ogni cella in nextCell, calcola la distanza dal bordo più vicino
        for (Cell cell : DataClass.getInstance().getNextCell()) {
            int x = cell.x;
            int y = cell.y;

            // Calcola la distanza dai quattro bordi della mappa
            int distanceToTop = x;
            int distanceToBottom = rows - 1 - x;
            int distanceToLeft = y;
            int distanceToRight = cols - 1 - y;

            // Trova la distanza minima dal bordo
            int minDistance = Math.min(Math.min(distanceToTop, distanceToBottom), Math.min(distanceToLeft, distanceToRight));

            // Stampa la distanza dal bordo più vicino
            variableProgram.addObjectInput(new EdgeDistance(x, y, minDistance));
        }
    }
    private void countEnemyEdgeDistance(Block[][] blocks, List<Cell> reachableEnemies) throws Exception {
        // Ottieni le dimensioni della mappa
        int rows = blocks.length;
        int cols = blocks[0].length;

        // Per ogni nemico raggiungibile, calcola la distanza dal bordo più vicino
        for (Cell cell : reachableEnemies) {
            int x = cell.x;
            int y = cell.y;

            // Calcola la distanza dai quattro bordi della mappa
            int distanceToTop = x;
            int distanceToBottom = rows - 1 - x;
            int distanceToLeft = y;
            int distanceToRight = cols - 1 - y;

            // Trova la distanza minima dal bordo
            int minDistance = Math.min(Math.min(distanceToTop, distanceToBottom), Math.min(distanceToLeft, distanceToRight));

            // Stampa la distanza dal bordo più vicino
            variableProgram.addObjectInput(new EdgeDistance(x, y, minDistance));
        }
    }
}

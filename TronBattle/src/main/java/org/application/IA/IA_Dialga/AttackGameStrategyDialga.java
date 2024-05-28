package org.application.IA.IA_Dialga;

import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import org.application.IA.IA_Dialga.asp_classes.attack.CellaDialga;
import org.application.IA.IA_Dialga.asp_classes.attack.CellaSceltaDialga;
import org.application.model.Block;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttackGameStrategyDialga extends StrategyDialga {

    InputProgram program = new ASPInputProgram();
    List<CellUtilDialga> cells = new ArrayList<>();
    private StrategyManagerDialga strategyManager;
    List<CellUtilDialga> path = new ArrayList<>();
    Map<int[], List<CellUtilDialga>> paths = new HashMap<>();
    List<CellaDialga> data = new ArrayList<>();

    AttackGameStrategyDialga(StrategyManagerDialga strategyManager) {
        this.strategyManager = strategyManager;
        program.addFilesPath("encodings/encodings_Dialga/ia-dialga-attack.asp");
        MainClassDialga.getInstance().addProgram(program);
    }

    @Override
    public int getDirection() {
            generateStrategy();
            getCell();
            path.remove(0);

        CellUtilDialga nextCell = path.get(0);
        System.out.println("Next cell: " + nextCell.getX() + " " + nextCell.getY());
        path.remove(0);
        return findDirection(nextCell.getX(), nextCell.getY());
    }

    private boolean isInvalidPath(List<CellUtilDialga> path, List<CellUtilDialga> reachableCells) {
        for (CellUtilDialga pathCell : path) {
            boolean cellFound = false;
            for (CellUtilDialga reachableCell : reachableCells) {
                if (pathCell.getX() == reachableCell.getX() && pathCell.getY() == reachableCell.getY()) {
                    cellFound = true;
                    if (reachableCell.getBlockType() != 0) {
                        return true; // Cella trovata, ma il blockType non è 0
                    }
                    break; // Esci dal loop interno poiché la cella è stata trovata ed è valida
                }
            }
            if (!cellFound) {
                return true; // Cella non trovata in reachableCells
            }
        }
        return false; // Tutte le celle nel path sono valide
    }



    private void getCell() {
        System.out.println(program.getPrograms());
        AnswerSet answerSets = MainClassDialga.getInstance().runASP();
        try {
            for (Object obj : answerSets.getAtoms()) {
                if (obj instanceof CellaSceltaDialga var) {
                    System.out.println("Cella scelta: " + var.getX() + " " + var.getY());
                    for (int[] cell : paths.keySet()) {
                        if (cell[0] == var.getX() && cell[1] == var.getY()) {
                            path = paths.get(cell);
                            break;
                        }
                    }
                }
            }
        } catch (InstantiationException | NoSuchMethodException |
                 IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateStrategy() {
        paths.clear();
        program.clearPrograms();
        cells.clear();
        path.clear();
        data.clear();
        //distanza da me
        cells = strategyManager.getCelleVicinoAlNemico();
        // distanza da nemico
        for (CellUtilDialga cell : cells) {
            CellaDialga c = new CellaDialga();
            for(CellUtilDialga cellEnemy:strategyManager.getReachableCellsEnemy()){
                if(cellEnemy.getX() == cell.getX() && cellEnemy.getY() == cell.getY()){
                    c.setDistanzaNemico(cell.getDistance());
                    break;
                }
            }
            c.setX(cell.getX());
            c.setY(cell.getY());
            c.setDistanzaDaMe(cell.getDistance());
            Block[][] var = MainClassDialga.getInstance().getCloneBlocks();
            List<CellUtilDialga> path = SearchManagerDialga.getInstance().findPath(
                    MainClassDialga.getInstance().getPlayerX(),
                    MainClassDialga.getInstance().getPlayerY(),
                    cell.getX(), cell.getY(), var);

            // Ottimizza l'aggiornamento della matrice di blocchi lungo il percorso
            var = updateBlocksAlongPath(var, path);

            paths.put(new int[]{cell.getX(), cell.getY()}, path);

            // Calcola le celle raggiungibili dal nemico
            CellUtilDialga firstEnemyCell = SearchManagerDialga.getInstance().findFirstEnemyCell(cell.getX(),cell.getY(),var, MainClassDialga.getInstance().getPlayer());

            int celleRaggiungibiliNemico = SearchManagerDialga.getInstance().findReachableCells(firstEnemyCell.getX(), firstEnemyCell.getY(), var).size();

            List<CellUtilDialga> reachables = SearchManagerDialga.getInstance().findReachableCells(cell.getX(),cell.getY(),var);
            int celleRaggiungibiliDaMe = reachables.size();

            // Aggiunge il risultato alla mappa `data`
            c.setCelleRaggiugibiliNemico(celleRaggiungibiliNemico);
            c.setCelleRaggiugibiliMe(celleRaggiungibiliDaMe);
            c.setNumeroNemici(SearchManagerDialga.getInstance().getEnemiesAround(reachables));
            data.add(c);
        }

        try {
            for (CellaDialga c : data) program.addObjectInput(c);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Integer getDistanzaDaMe(int i, int i1) {
        for (CellUtilDialga cell : strategyManager.getReachableCells()) {
            if (cell.getX() == i && cell.getY() == i1) {
                return cell.getDistance();
            }
        }
        return 0;
    }

}

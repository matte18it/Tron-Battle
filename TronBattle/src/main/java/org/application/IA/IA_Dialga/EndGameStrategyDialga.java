package org.application.IA.IA_Dialga;

import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import org.application.IA.IA_Dialga.asp_classes.end_game_complete.InPathDialga;
import org.application.IA.IA_Dialga.asp_classes.end_game_complete.NodeDialga;
import org.application.IA.IA_Dialga.asp_classes.end_game_complete.StartDialga;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class EndGameStrategyDialga extends StrategyDialga {

    InputProgram program = new ASPInputProgram();
    private final List<InPathDialga> path = new ArrayList<>();
    private final StrategyManagerDialga manager;

    EndGameStrategyDialga(StrategyManagerDialga manager) {
        this.manager = manager;
        program.addFilesPath("encodings/encodings_Dialga/ia-dialga-end-game.asp");
        MainClassDialga.getInstance().addProgram(program);
    }

    private void generateStrategy() {
        program.clearPrograms();
        try {
            program.addObjectInput(new StartDialga(MainClassDialga.getInstance().getPlayerX(), MainClassDialga.getInstance().getPlayerY()));
            program.addObjectInput(new NodeDialga(MainClassDialga.getInstance().getPlayerX(), MainClassDialga.getInstance().getPlayerY()));
            for (CellUtilDialga cell : manager.getReachableCells()) {
                if (cell.getBlockType() == 0)
                    program.addObjectInput(new NodeDialga(cell.getX(), cell.getY()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getDirection() {
        if (path.isEmpty()) {
            generateStrategy();
            getPath();
        }
        for (InPathDialga inPath : path) {
            if (inPath.getX() == MainClassDialga.getInstance().getPlayerX() && inPath.getY() == MainClassDialga.getInstance().getPlayerY()) {
                path.remove(inPath);
                return findDirection(inPath.getX1(), inPath.getY1());
            }
        }
        return 0;
    }

    private void getPath() {
        System.out.println(program.getPrograms());
        AnswerSet answerSets = MainClassDialga.getInstance().runASP();
        try {
            for (Object obj : answerSets.getAtoms()) {
                if (obj instanceof InPathDialga var) {
                    path.add(var);
                }
            }

        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}

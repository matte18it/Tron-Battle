package org.application.IA.IA_Dialga;

import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.SymbolicConstant;
import org.application.IA.IA_Dialga.asp_classes.MossaDialga;
import org.application.IA.IA_Dialga.asp_classes.MossaSceltaDialga;
import org.application.IA.IA_Dialga.asp_classes.end_game_fast.CelleNemicoNonRaggiungibiliDialga;
import org.application.IA.IA_Dialga.asp_classes.end_game_fast.CelleNonRaggiungibiliDialga;
import org.application.IA.IA_Dialga.asp_classes.end_game_fast.CelleVuoteVicineDialga;
import org.application.model.Block;
import org.application.utility.Settings;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefenseStrategyDialga extends StrategyDialga {

    InputProgram program = new ASPInputProgram();

    DefenseStrategyDialga() {
        program.addFilesPath("encodings/encodings_Dialga/ia-dialga-defense.asp");
        MainClassDialga.getInstance().addProgram(program);

    }

    private void generateStrategy() {
        program.clearPrograms();
        try {
            Block[][] constBlock = MainClassDialga.getInstance().getCloneBlocks();
            int playerX = MainClassDialga.getInstance().getPlayerX();
            int playerY = MainClassDialga.getInstance().getPlayerY();
            int player = MainClassDialga.getInstance().getPlayer();
            int playerBody = MainClassDialga.getInstance().getPlayerBody();
            if (MainClassDialga.getInstance().isValidMove(constBlock, playerX + 1, playerY)) {
                Block[][] blocks = MainClassDialga.getInstance().getCloneBlocks();
                blocks[playerX][playerY] = new Block(playerBody);
                blocks[playerX + 1][playerY] = new Block(player);
                List<CellUtilDialga> reachableCells = SearchManagerDialga.getInstance().findReachableCells(playerX+1, playerY, blocks);
                int unreachableCells = (blocks.length * blocks[0].length) - reachableCells.size();
                Map<Integer, Integer> celleNemicheNonRaggiungibili = trovaCelleRaggiungibiliDaNemici(blocks);
                for (Map.Entry<Integer, Integer> entry : celleNemicheNonRaggiungibili.entrySet()) {
                    program.addObjectInput(new CelleNemicoNonRaggiungibiliDialga(entry.getKey(), new SymbolicConstant("right"), entry.getValue()));
                }
                program.addObjectInput(new MossaDialga(new SymbolicConstant("right")));
                program.addObjectInput(new CelleNonRaggiungibiliDialga(new SymbolicConstant("right"), unreachableCells));
                program.addObjectInput(new CelleVuoteVicineDialga(new SymbolicConstant("right"), getCelleVuoteVicine(playerX + 1, playerY, blocks)));
            }
            if (MainClassDialga.getInstance().isValidMove(constBlock, playerX - 1, playerY)) {
                Block[][] blocks = MainClassDialga.getInstance().getCloneBlocks();
                blocks[playerX][playerY] = new Block(playerBody);
                blocks[playerX - 1][playerY] = new Block(player);
                List<CellUtilDialga> reachableCells = SearchManagerDialga.getInstance().findReachableCells(playerX-1, playerY,blocks);
                int unreachableCells = (blocks.length * blocks[0].length) - reachableCells.size();
                Map<Integer, Integer> celleNemicheNonRaggiungibili = trovaCelleRaggiungibiliDaNemici(blocks);
                for (Map.Entry<Integer, Integer> entry : celleNemicheNonRaggiungibili.entrySet()) {
                    program.addObjectInput(new CelleNemicoNonRaggiungibiliDialga(entry.getKey(), new SymbolicConstant("left"), entry.getValue()));
                }
                program.addObjectInput(new MossaDialga(new SymbolicConstant("left")));
                program.addObjectInput(new CelleNonRaggiungibiliDialga(new SymbolicConstant("left"), unreachableCells));
                program.addObjectInput(new CelleVuoteVicineDialga(new SymbolicConstant("left"), getCelleVuoteVicine(playerX - 1, playerY, blocks)));
            }
            if (MainClassDialga.getInstance().isValidMove(constBlock, playerX, playerY + 1)) {
                Block[][] blocks = MainClassDialga.getInstance().getCloneBlocks();
                blocks[playerX][playerY] = new Block(playerBody);
                blocks[playerX][playerY + 1] = new Block(player);
                List<CellUtilDialga> reachableCells = SearchManagerDialga.getInstance().findReachableCells(playerX,playerY+1, blocks);
                int unreachableCells = (blocks.length * blocks[0].length) - reachableCells.size();
                Map<Integer, Integer> celleNemicheNonRaggiungibili = trovaCelleRaggiungibiliDaNemici(blocks);
                for (Map.Entry<Integer, Integer> entry : celleNemicheNonRaggiungibili.entrySet()) {
                    program.addObjectInput(new CelleNemicoNonRaggiungibiliDialga(entry.getKey(), new SymbolicConstant("down"), entry.getValue()));
                }
                program.addObjectInput(new MossaDialga(new SymbolicConstant("down")));
                program.addObjectInput(new CelleNonRaggiungibiliDialga(new SymbolicConstant("down"), unreachableCells));
                program.addObjectInput(new CelleVuoteVicineDialga(new SymbolicConstant("down"), getCelleVuoteVicine(playerX, playerY + 1, blocks)));
            }
            if (MainClassDialga.getInstance().isValidMove(constBlock, playerX, playerY - 1)) {
                Block[][] blocks = MainClassDialga.getInstance().getCloneBlocks();
                blocks[playerX][playerY] = new Block(playerBody);
                blocks[playerX][playerY - 1] = new Block(player);
                List<CellUtilDialga> reachableCells = SearchManagerDialga.getInstance().findReachableCells(playerX,playerY-1,blocks);
                int unreachableCells = (blocks.length * blocks[0].length) - reachableCells.size();
                Map<Integer, Integer> celleNemicheNonRaggiungibili = trovaCelleRaggiungibiliDaNemici(blocks);
                for (Map.Entry<Integer, Integer> entry : celleNemicheNonRaggiungibili.entrySet()) {
                    program.addObjectInput(new CelleNemicoNonRaggiungibiliDialga(entry.getKey(), new SymbolicConstant("up"), entry.getValue()));
                }
                program.addObjectInput(new MossaDialga(new SymbolicConstant("up")));
                program.addObjectInput(new CelleNonRaggiungibiliDialga(new SymbolicConstant("up"), unreachableCells));
                program.addObjectInput(new CelleVuoteVicineDialga(new SymbolicConstant("up"), getCelleVuoteVicine(playerX, playerY - 1, blocks)));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private Map<Integer, Integer> trovaCelleRaggiungibiliDaNemici(Block[][] blocks) {
        Map<Integer, Integer> celleNemicheNonRaggiungibili = new HashMap<>();

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j].type() > 0 && blocks[i][j].type() < 5 && blocks[i][j].type() != MainClassDialga.getInstance().getPlayer()) {
                    List<CellUtilDialga> reachableCells = SearchManagerDialga.getInstance().findReachableCells(i,j,blocks);
                    int unreachableCells = (blocks.length * blocks[0].length) - reachableCells.size();
                    celleNemicheNonRaggiungibili.put(blocks[i][j].type(), unreachableCells);
                }
            }
        }
        return celleNemicheNonRaggiungibili;
    }

    private int getCelleVuoteVicine(int playerX, int playerY, Block[][] blocks) {
        int count = 0;
        if (MainClassDialga.getInstance().isValidMove(blocks, playerX + 1, playerY)) {
            count++;
        }
        if (MainClassDialga.getInstance().isValidMove(blocks, playerX - 1, playerY)) {
            count++;
        }
        if (MainClassDialga.getInstance().isValidMove(blocks, playerX, playerY + 1)) {
            count++;
        }
        if (MainClassDialga.getInstance().isValidMove(blocks, playerX, playerY - 1)) {
            count++;
        }
        return count;
    }

    @Override
    public int getDirection() {
        generateStrategy();
        return extractDirection();

    }

    private int extractDirection() {
        System.out.println(program.getPrograms());
        AnswerSet answerSets = MainClassDialga.getInstance().runASP();
        try {

            for (Object obj : answerSets.getAtoms()) {
                if (obj instanceof MossaSceltaDialga mossa) {
                    switch (mossa.getDirection().toString()) {
                        case "left":
                            return Settings.LEFT;
                        case "right":
                            return Settings.RIGHT;
                        case "up":
                            return Settings.UP;
                        case "down":
                            return Settings.DOWN;

                    }
                }
            }

        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

}

package org.application.IA.IA_Dialga;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.*;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.platforms.desktop.DesktopService;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import lombok.Getter;
import org.application.IA.IA_Dialga.asp_classes.MossaDialga;
import org.application.IA.IA_Dialga.asp_classes.MossaSceltaDialga;
import org.application.IA.IA_Dialga.asp_classes.attack.CellaDialga;
import org.application.IA.IA_Dialga.asp_classes.attack.CellaSceltaDialga;
import org.application.IA.IA_Dialga.asp_classes.end_game_complete.InPathDialga;
import org.application.IA.IA_Dialga.asp_classes.end_game_complete.NodeDialga;
import org.application.IA.IA_Dialga.asp_classes.end_game_complete.StartDialga;
import org.application.IA.IA_Dialga.asp_classes.end_game_fast.CelleNemicoNonRaggiungibiliDialga;
import org.application.IA.IA_Dialga.asp_classes.end_game_fast.CelleNonRaggiungibiliDialga;
import org.application.IA.IA_Dialga.asp_classes.end_game_fast.CelleVuoteVicineDialga;
import org.application.IA.IA_Dialga.asp_classes.explorer.ZonaDialga;
import org.application.IA.IA_Dialga.asp_classes.explorer.ZonaSceltaDialga;
import org.application.model.Block;

import java.util.List;
import java.util.Random;

public class MainClassDialga {

    @Getter
    private int playerX = 0;
    @Getter
    private Block[][] blocks;
    @Getter
    private int playerY = 0;
    @Getter
    private int player;
    @Getter
    private int playerBody;
    private static MainClassDialga instance = null;
    //linux
    private final DesktopService service = new DLV2DesktopService("lib/dlv-2");
    //win
    //private final DesktopService service = new DLV2DesktopService("lib/dlv2.exe");
    private final Handler handler = new DesktopHandler(service);
    private StrategyManagerDialga strategy = null;

    private MainClassDialga() {
    }

    public static MainClassDialga getInstance() {
        if (instance == null)
            instance = new MainClassDialga();
        return instance;
    }

    public void init() {
        strategy = new StrategyManagerDialga();
        OptionDescriptor option = new OptionDescriptor("-n0");
        OptionDescriptor option2 = new OptionDescriptor(" -n 1");
        handler.addOption(option);
        handler.addOption(option2);
        try {
            ASPMapper.getInstance().registerClass(MossaDialga.class);
            ASPMapper.getInstance().registerClass(MossaSceltaDialga.class);
            ASPMapper.getInstance().registerClass(CelleNonRaggiungibiliDialga.class);
            ASPMapper.getInstance().registerClass(CelleVuoteVicineDialga.class);
            ASPMapper.getInstance().registerClass(InPathDialga.class);
            ASPMapper.getInstance().registerClass(NodeDialga.class);
            ASPMapper.getInstance().registerClass(StartDialga.class);
            ASPMapper.getInstance().registerClass(ZonaSceltaDialga.class);
            ASPMapper.getInstance().registerClass(ZonaDialga.class);
            ASPMapper.getInstance().registerClass(CellaDialga.class);
            ASPMapper.getInstance().registerClass(CellaSceltaDialga.class);
            ASPMapper.getInstance().registerClass(CelleNemicoNonRaggiungibiliDialga.class);
        } catch (IllegalAnnotationException | ObjectNotValidException e) {
            e.printStackTrace();
        }
    }

    public int getDirection(Block[][] blocks, int player, int playerBody) {
        /*
        * Metodo che restituisce la direzione del player
        * 0 = destra, 1 = sinistra, 2 = su, 3 = giù
        */
        this.blocks = blocks;
        this.player = player;
        this.playerBody = playerBody;
        getPlayerPosition();
        return strategy.getDirection();
    }

    private void getPlayerPosition() {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j].type() == player) {
                    playerX = i;
                    playerY = j;
                }
            }
        }
    }

    AnswerSet runASP() {
        Output output = handler.startSync();
        AnswerSets answersets = (AnswerSets) output;
        try {
            Random random = new Random();
            List<AnswerSet> objects = answersets.getOptimalAnswerSets();
            return objects.get(random.nextInt(objects.size()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    boolean isValidMove(Block[][] blocks, int x, int y) {
        return x >= 0 && x < blocks.length && y >= 0 && y < blocks[x].length && blocks[x][y].type() == 0;
    }

     Block[][] getCloneBlocks() {
        Block[][] clone = new Block[blocks.length][blocks[0].length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                clone[i][j] = new Block(blocks[i][j].type());
            }
        }
        return clone;
    }

     void addProgram(InputProgram program) {
        handler.removeProgram(0);
        handler.addProgram(program);
    }

}

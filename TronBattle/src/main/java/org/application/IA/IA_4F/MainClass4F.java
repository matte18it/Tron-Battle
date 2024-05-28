package org.application.IA.IA_4F;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import org.application.model.Block;

public class MainClass4F {
    //Attributi
    private static MainClass4F instance = null;
    private static String fixedProgramPath = "encodings/encodings_4F/IAMota.txt";
    private static String variableProgramPath = "";
    private static final InputProgram variableProgram = new ASPInputProgram();
    private static final InputProgram fixedProgram = new ASPInputProgram();
    private static Handler handler;


    //Costruttore
    private MainClass4F() {}

    //Metodi
    public static MainClass4F getInstance() {
        // funzione per ottenere l'istanza del MainClass4F
        if(instance == null)
            instance = new MainClass4F();
        return instance;
    }

    public void init(){
        // metodo per inizializzare i file DLV dell'IA
        handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));

        //opzioni per l'handler
        //OptionDescriptor optionDescriptorNoFacts = new OptionDescriptor("--no-facts"); //elimina i fatti
        //OptionDescriptor optionDescriptorAllAS = new OptionDescriptor("-n 0"); //mostra tutti gli answer set

        //handler.addOption(optionDescriptorNoFacts);
        //handler.addOption(optionDescriptorAllAS);
        fixedProgram.addFilesPath(fixedProgramPath);
        variableProgram.addFilesPath(variableProgramPath);
        handler.addProgram(fixedProgram);
        handler.addProgram(variableProgram);
        try {
            ASPMapper.getInstance().registerClass(Position.class);
            ASPMapper.getInstance().registerClass(Movement.class);
            ASPMapper.getInstance().registerClass(Count.class);
        } catch (ObjectNotValidException | IllegalAnnotationException exception) {
            exception.printStackTrace();
        }


    }

    public int getDirection(Block[][] blocks, int playerHead, int playerBody)  {
        // Questo metodo ritorna la direzione in cui il personaggio deve muoversi
        // 0 = destra, 1 = sinistra, 2 = su, 3 = giù

        variableProgram.clearAll();

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j].type() == playerHead) {
                    try {
                        //System.out.println("POSIZIONE TESTA: ["+j+"]"+"["+i+"]");
                        Count count = countFreeCell(blocks, j, i);
                        variableProgram.addObjectInput(new Position(j,i));
                        variableProgram.addObjectInput(count);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //System.out.println("FATTI :"+variableProgram.getPrograms());

        Output o = handler.startSync();
        AnswerSets answerSets = (AnswerSets) o;

        /*System.out.println("Numero di as: " + answerSets.getAnswersets().size());
        if(answerSets.getAnswersets().isEmpty()){
            System.out.println("NESSUN ANSWER SET TROVATO");
        }*/
        //System.out.println("ANSWER SETS: "+answerSets.getOptimalAnswerSets());

        for (AnswerSet a: answerSets.getOptimalAnswerSets()){
            //System.out.println("ANSWER SET: "+a);
            try{
                for (Object obj : a.getAtoms()) {
                    if (!(obj instanceof Movement))continue;{
                        Movement m = (Movement) obj;
                        switch (m.getDirection()){
                            case 0:
                                //System.out.println("MOVIMENTO: DESTRA");
                                break;
                            case 1:
                                //System.out.println("MOVIMENTO: SINISTRA");
                                break;
                            case 2:
                                //System.out.println("MOVIMENTO: SU");
                                break;
                            case 3:
                                //System.out.println("MOVIMENTO: GIU");
                                break;
                        }
                        return m.getDirection();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return -1;
    }

    public Count countFreeCell(Block[][] blocks, int X, int Y) {
        int up = 0;
        int down = 0;
        int left = 0;
        int right = 0;
        //64 - 40
        //System.out.println(blocks.length+" "+blocks[0].length);
        for (int i = X + 1; i < blocks[0].length - 1; i++) {
            if (blocks[Y][i].type() == 0) {
                down++;
            } else {
                break;
            }
        }

        for (int i = X - 1; i >= 0; i--) {
            if (blocks[Y][i].type() == 0) {
                up++;
            } else {
                break;
            }
        }

        for (int j = Y + 1; j < blocks.length-1; j++) {
            if (blocks[j][X].type() == 0) {
                right++;
            } else {
                break;
            }
        }

        for (int j = Y - 1; j >= 0; j--) {
            if (blocks[j][X].type() == 0) {
                left++;
            } else {
                break;
            }
        }
        //System.out.println("UP: "+up+" DOWN: "+down+" LEFT: "+left+" RIGHT: "+right);
        return new Count(right, left, up, down);
    }
}




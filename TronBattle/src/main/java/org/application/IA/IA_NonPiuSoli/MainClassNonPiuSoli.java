
package org.application.IA.IA_NonPiuSoli;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
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



import java.util.*;

import static org.application.IA.IA_NonPiuSoli.AStar.findPath;

public class MainClassNonPiuSoli {
    private static MainClassNonPiuSoli instance = null;
    private static Handler handler;
//    private static final InputProgram fisso = new ASPInputProgram();
    private static final InputProgram attacco = new ASPInputProgram();
    private static final InputProgram conquistaTerritorio = new ASPInputProgram();

    //servono per determinare la posizione iniziale del giocatore
    //private  boolean controlloPosizioneIniziale = false;
    //private  int posizioneInizialeX = 0;
    //private  int posizioneInizialeY = 0;
    private int ultimaDirezione = 0;



    private MainClassNonPiuSoli() {}

    public static MainClassNonPiuSoli getInstance() {
        // funzione per ottenere l'istanza del MainClassNonPiuSoli
        if (instance == null)
            instance = new MainClassNonPiuSoli();
        return instance;
    }

    public void init() {
        // metodo per inizializzare i file DLV dell'IA
        //linux
        //handler = new DesktopHandler(new DLV2DesktopService("lib/dlv-2"));
        //win
        handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2.exe"));
        //mac
        //handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
        OptionDescriptor option = new OptionDescriptor("-n 0");
        handler.addOption(option);
        attacco.addFilesPath("encodings/encodings_NonPiuSoli/attacco");
        conquistaTerritorio.addFilesPath("encodings/encodings_NonPiuSoli/conquistaTerritorio");
        handler.addProgram(attacco);
        try {
            ASPMapper.getInstance().registerClass(Direction.class);
            ASPMapper.getInstance().registerClass(Distance.class);
            ASPMapper.getInstance().registerClass(FreeSpace.class);
            ASPMapper.getInstance().registerClass(RiskTrap.class);
            ASPMapper.getInstance().registerClass(Move.class);
            ASPMapper.getInstance().registerClass(Mossa.class);
            //ASPMapper.getInstance().registerClass(InitialPosition.class);
            ASPMapper.getInstance().registerClass(Distance.class);
            ASPMapper.getInstance().registerClass(PlayerPosition.class);
        } catch (ObjectNotValidException | IllegalAnnotationException e) {
            throw new RuntimeException(e);
        }

    }

    public int getDirection(Block[][] blocks, int playerHead, int playerBody) throws Exception {
        int playerPositionX = -1;
        int playerPositionY = -1;
        int countCelleOccupate = 0;

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j].type() == playerHead) {
                    playerPositionX = i; playerPositionY = j;
                }
                if (blocks[i][j].type() != 0) {
                    countCelleOccupate++;
                }
                /*
                if(! controlloPosizioneIniziale){
                    posizioneInizialeX = playerPositionX;
                    posizioneInizialeY = playerPositionY;
                    controlloPosizioneIniziale = true;
                    conquistaTerritorio.addObjectInput((new InitialPosition(posizioneInizialeX, posizioneInizialeY)));
                }
                */
            }
        }

        //System.out.println("countCelleOccupate: " + countCelleOccupate);
        if (countCelleOccupate > 100) {
            handler.removeProgram(0);
            handler.addProgram(attacco);
            attacco.clearPrograms();
            if (playerPositionX != -1) {
                if (playerPositionX + 1 < blocks.length && blocks[playerPositionX + 1][playerPositionY].type() == Block.EMPTY) {
                    int distanceRight = bfs(blocks, playerPositionX + 1, playerPositionY, playerHead);
                    int freeSpaceRight = calculateFreeSpace(blocks, playerPositionX + 1, playerPositionY);
                    int riskTrapRight = calculateRiskTrap(blocks, playerPositionX + 1, playerPositionY);
                    attacco.addObjectInput(new Distance(distanceRight, 0));
                    attacco.addObjectInput(new FreeSpace(freeSpaceRight, 0));
                    attacco.addObjectInput(new RiskTrap(riskTrapRight, 0));
                    attacco.addObjectInput(new Direction(0));
                }
                if (playerPositionX - 1 >= 0 && blocks[playerPositionX - 1][playerPositionY].type() == Block.EMPTY) {
                    int distanceLeft = bfs(blocks, playerPositionX - 1, playerPositionY, playerHead);
                    int freeSpaceLeft = calculateFreeSpace(blocks, playerPositionX - 1, playerPositionY);
                    int riskTrapLeft = calculateRiskTrap(blocks, playerPositionX - 1, playerPositionY);
                    attacco.addObjectInput(new Distance(distanceLeft, 1));
                    attacco.addObjectInput(new FreeSpace(freeSpaceLeft, 1));
                    attacco.addObjectInput(new RiskTrap(riskTrapLeft, 1));
                    attacco.addObjectInput(new Direction(1));
                }
                if (playerPositionY - 1 >= 0 && blocks[playerPositionX][playerPositionY - 1].type() == Block.EMPTY) {
                    int distanceUp = bfs(blocks, playerPositionX, playerPositionY - 1, playerHead);
                    int freeSpaceUp = calculateFreeSpace(blocks, playerPositionX, playerPositionY - 1);
                    int riskTrapUp = calculateRiskTrap(blocks, playerPositionX, playerPositionY - 1);
                    attacco.addObjectInput(new Distance(distanceUp, 2));
                    attacco.addObjectInput(new FreeSpace(freeSpaceUp, 2));
                    attacco.addObjectInput(new RiskTrap(riskTrapUp, 2));
                    attacco.addObjectInput(new Direction(2));
                }
                if (playerPositionY + 1 < blocks[0].length && blocks[playerPositionX][playerPositionY + 1].type() == Block.EMPTY) {
                    int distanceDown = bfs(blocks, playerPositionX, playerPositionY + 1, playerHead);
                    int freeSpaceDown = calculateFreeSpace(blocks, playerPositionX, playerPositionY + 1);
                    int riskTrapDown = calculateRiskTrap(blocks, playerPositionX, playerPositionY + 1);
                    attacco.addObjectInput(new Distance(distanceDown, 3));
                    attacco.addObjectInput(new FreeSpace(freeSpaceDown, 3));
                    attacco.addObjectInput(new RiskTrap(riskTrapDown, 3));
                    attacco.addObjectInput(new Direction(3));
                }
            }
        }
        else {
            handler.removeProgram(0);
            handler.addProgram(conquistaTerritorio);
            conquistaTerritorio.clearPrograms();
            //System.out.println("--------------------------------------------> entro nell'else");

            conquistaTerritorio.addObjectInput(new PlayerPosition(playerPositionX, playerPositionY));
            // codice per conquistare spazio

            //se la moto si trova all'angolo 0,0
            if (playerPositionX == 0 && playerPositionY == 0) {

                //controllo se sono occupati i blocchi in diagonale
                if (blocks[playerPositionX + 1][playerPositionY + 1].type() != Block.EMPTY) {
                    conquistaTerritorio.addObjectInput(new Occupato(playerPositionX + 1, playerPositionY + 1));
                    //System.out.println(" a x: " + (playerPositionX + 1) + " y: " + (playerPositionY + 1) + " occupato");
                }

            }

            //se la moto si trova all'angolo 63,0
            if (playerPositionX == 63 && playerPositionY == 0) {

                //controllo se sono occupati i blocchi in diagonale
                if (blocks[playerPositionX - 1][playerPositionY + 1].type() != Block.EMPTY) {
                    conquistaTerritorio.addObjectInput(new Occupato(playerPositionX - 1, playerPositionY + 1));
                    //System.out.println(" b x: " + (playerPositionX - 1) + " y: " + (playerPositionY + 1) + " occupato");
                }


            }

            //se la moto si trova all'angolo 63,39
            if (playerPositionX == 63 && playerPositionY == 39) {

                //controllo se sono occupati i blocchi in diagonale
                if (blocks[playerPositionX - 1][playerPositionY - 1].type() != Block.EMPTY) {
                    conquistaTerritorio.addObjectInput(new Occupato(playerPositionX - 1, playerPositionY - 1));
                    //System.out.println(" c x: " + (playerPositionX - 1) + " y: " + (playerPositionY - 1) + " occupato");
                }


            }

            //se la moto si trova all'angolo 0,39
            if (playerPositionX == 0 && playerPositionY == 39) {

                //controllo se sono occupati i blocchi in diagonale
                if (blocks[playerPositionX + 1][playerPositionY - 1].type() != Block.EMPTY) {
                    conquistaTerritorio.addObjectInput(new Occupato(playerPositionX + 1, playerPositionY - 1));
                    //System.out.println(" d x: " + (playerPositionX + 1) + " y: " + (playerPositionY - 1) + " occupato");
                }


            }

            //se la moto si trova sul bordo in alto (non agli angoli)
            if (playerPositionY == 0 && (playerPositionX > 0 && playerPositionX < 63)) {

                //controllo se sono occupati i blocchi in diagonale (sinistra)
                if (blocks[playerPositionX - 1][playerPositionY + 1].type() != Block.EMPTY) {
                    conquistaTerritorio.addObjectInput(new Occupato(playerPositionX - 1, playerPositionY + 1));
                    //System.out.println(" e x: " + (playerPositionX - 1) + " y: " + (playerPositionY + 1) + " occupato");
                }
                //controllo se sono occupati i blocchi in diagonale (destra)
                if (blocks[playerPositionX + 1][playerPositionY + 1].type() != Block.EMPTY) {
                    conquistaTerritorio.addObjectInput(new Occupato(playerPositionX + 1, playerPositionY + 1));
                    //System.out.println(" f x: " + (playerPositionX + 1) + " y: " + (playerPositionY + 1) + " occupato");
                }


            }

            //se la moto si trova sul bordo in basso (non agli angoli)
            if (playerPositionY == 39 && (playerPositionX > 0 && playerPositionX < 63)) {

                //controllo se sono occupati i blocchi in diagonale (sinistra)
                if (blocks[playerPositionX - 1][playerPositionY - 1].type() != Block.EMPTY) {
                    conquistaTerritorio.addObjectInput(new Occupato(playerPositionX - 1, playerPositionY - 1));
                    //System.out.println(" g x: " + (playerPositionX - 1) + " y: " + (playerPositionY - 1) + " occupato");
                }

                //controllo se sono occupati i blocchi in diagonale (destra)
                if (blocks[playerPositionX + 1][playerPositionY - 1].type() != Block.EMPTY) {
                    conquistaTerritorio.addObjectInput(new Occupato(playerPositionX + 1, playerPositionY - 1));
                    //System.out.println(" h x: " + (playerPositionX + 1) + " y: " + (playerPositionY - 1) + " occupato");
                }



            }
            //se la moto si trova sul bordo a sinistra (non agli angoli)
            if (playerPositionX == 0 && (playerPositionY > 0 && playerPositionY < 39)) {


                //se mi trovo in posizione 0 1 controllo la posizione a destra
                if (playerPositionX == 0 && playerPositionY == 1) {
                    if (blocks[playerPositionX + 1][playerPositionY].type() != Block.EMPTY) {
                        conquistaTerritorio.addObjectInput(new Occupato(playerPositionX + 1, playerPositionY));
                        //System.out.println(" ii x: " + (playerPositionX + 1) + " y: " + (playerPositionY) + " occupato");
                    }
                }


                //se mi trovo in posizione 0 38 controllo la posizione a destra
                if (playerPositionX == 0 && playerPositionY == 38) {
                    if (blocks[playerPositionX + 1][playerPositionY].type() != Block.EMPTY) {
                        conquistaTerritorio.addObjectInput(new Occupato(playerPositionX + 1, playerPositionY));
                        //System.out.println(" ii x: " + (playerPositionX + 1) + " y: " + (playerPositionY) + " occupato");
                    }
                }


                //controllo se sono occupati i blocchi in diagonale (sopra)
                if (blocks[playerPositionX + 1][playerPositionY - 1].type() != Block.EMPTY) {
                    conquistaTerritorio.addObjectInput(new Occupato(playerPositionX + 1, playerPositionY - 1));
                   // System.out.println(" i x: " + (playerPositionX + 1) + " y: " + (playerPositionY - 1) + " occupato");
                }


                //controllo se sono occupati i blocchi in diagonale (sotto)
                if (blocks[playerPositionX + 1][playerPositionY + 1].type() != Block.EMPTY) {
                    conquistaTerritorio.addObjectInput(new Occupato(playerPositionX + 1, playerPositionY + 1));
                    //System.out.println(" j x: " + (playerPositionX + 1) + " y: " + (playerPositionY + 1) + " occupato");
                }


            }


            //se la moto si trova sul bordo a destra (non agli angoli)
            if (playerPositionX == 63 && (playerPositionY > 0 && playerPositionY < 39)) {


                //se mi trovo in posizione 63 1 controllo il blocco a sinistra
                if (playerPositionX == 63 && playerPositionY == 1) {
                    if (blocks[playerPositionX - 1][playerPositionY].type() != Block.EMPTY) {
                        conquistaTerritorio.addObjectInput(new Occupato(playerPositionX - 1, playerPositionY));
                        //System.out.println(" k x: " + (playerPositionX - 1) + " y: " + (playerPositionY) + " occupato");
                    }
                }

                //se mi trovo in posizione 63 38 controllo il blocco a sinistra
                if (playerPositionX == 63 && playerPositionY == 38) {
                    if (blocks[playerPositionX - 1][playerPositionY].type() != Block.EMPTY) {
                        conquistaTerritorio.addObjectInput(new Occupato(playerPositionX - 1, playerPositionY));
                        //System.out.println(" k x: " + (playerPositionX - 1) + " y: " + (playerPositionY) + " occupato");
                    }
                }

                //controllo se sono occupati i blocchi in diagonale (sopra)
                if (blocks[playerPositionX - 1][playerPositionY - 1].type() != Block.EMPTY) {
                    conquistaTerritorio.addObjectInput(new Occupato(playerPositionX - 1, playerPositionY - 1));
                    //System.out.println(" k x: " + (playerPositionX - 1) + " y: " + (playerPositionY - 1) + " occupato");
                }

                //controllo se sono occupati i blocchi in diagonale (sotto)
                if (blocks[playerPositionX - 1][playerPositionY + 1].type() != Block.EMPTY) {
                    conquistaTerritorio.addObjectInput(new Occupato(playerPositionX - 1, playerPositionY + 1));
                    //System.out.println(" l x: " + (playerPositionX - 1) + " y: " + (playerPositionY + 1) + " occupato");
                }


            }
            //se la moto non si trova ne ai bordi e nemmeno agli angoli
            if ((playerPositionX > 0 && playerPositionX < 63) && (playerPositionY > 0 && playerPositionY < 39)) {

                //System.out.println("entro -------------------------------------------------------------");

                //controllo se sono occupati i blocchi in diagonale (in alto a destra)
                if (blocks[playerPositionX + 1][playerPositionY - 1].type() != Block.EMPTY) {
                    conquistaTerritorio.addObjectInput(new Occupato(playerPositionX + 1, playerPositionY - 1));
                    //System.out.println(" m x: " + (playerPositionX + 1) + " y: " + (playerPositionY - 1) + " occupato");
                }

                //controllo se sono occupati i blocchi in diagonale (in basso a destra)
                if (blocks[playerPositionX + 1][playerPositionY + 1].type() != Block.EMPTY) {
                    conquistaTerritorio.addObjectInput(new Occupato(playerPositionX + 1, playerPositionY + 1));
                    //System.out.println(" n x: " + (playerPositionX + 1) + " y: " + (playerPositionY + 1) + " occupato");
                }

                //controllo se sono occupati i blocchi in diagonale (in basso a sinistra)
                if (blocks[playerPositionX - 1][playerPositionY + 1].type() != Block.EMPTY) {
                    conquistaTerritorio.addObjectInput(new Occupato(playerPositionX - 1, playerPositionY + 1));
                    //System.out.println(" o x: " + (playerPositionX - 1) + " y: " + (playerPositionY + 1) + " occupato");
                }

                //controllo se sono occupati i blocchi in diagonale (sinistra)
                if (blocks[playerPositionX - 1][playerPositionY - 1].type() != Block.EMPTY) {
                    conquistaTerritorio.addObjectInput(new Occupato(playerPositionX - 1, playerPositionY - 1));
                    //System.out.println(" p x: " + (playerPositionX - 1) + " y: " + (playerPositionY - 1) + " occupato");
                }



            }


            HashSet<Node> closedList = new HashSet<>();

            int num1;
            int num2;
            List<Node> path;
            List<Node> path1;
            int count = 1;

            do {


                int[] lista = getGoal(blocks);
                num1 = lista[0];
                num2 = lista[1];



                path = percorso(blocks, playerPositionX, playerPositionY, num1, num2, closedList);
                path1 = percorso(blocks, playerPositionX, playerPositionY, num1, num2, closedList);




                count+=1;
                //System.out.println("count------------------------> "+count);

                if(count == 10)
                    break;


            }while (path.size()<1 && path1.size()<1);

            if(path.size()>1) {
                int direzione1 = calcolaDirezione(path.get(1), playerPositionX, playerPositionY);
                conquistaTerritorio.addObjectInput(new Mossa(direzione1));
            }

            if(path1.size()>1) {
                int direzione2 = calcolaDirezione(path1.get(1), playerPositionX, playerPositionY);
                conquistaTerritorio.addObjectInput(new Mossa(direzione2));
            }


            if(path.size()<1 && path1.size()<1){
                //System.out.println("inserisco l'ultima direzione:"+ultimaDirezione );
                conquistaTerritorio.addObjectInput(new Mossa(ultimaDirezione));
            }


        }

        // Log per debug
        //System.out.println("Fatti inviati:");
        //System.out.println(attacco.getPrograms());
        //System.out.println();

        Output o = handler.startSync();

        //System.out.println(o.getOutput());
        //System.out.println("Errori del solver:");
        //System.out.println(o.getErrors());

        AnswerSets answersets = (AnswerSets) o;

        if (answersets.getAnswersets().isEmpty()) {
            //System.out.println("Nessun risultato restituito dall'ASP.");
            return 0;
        }

        List<AnswerSet> answerSets = answersets.getAnswersets();
        if (answerSets.isEmpty()) {
            //System.out.println("Nessuna configurazione ottimale trovata.");
            return 0;
        }

        for (AnswerSet a : answerSets) {
            //System.out.println("Risultato ASP:");
            //System.out.println(a.toString());
            //System.out.println();

            for (Object obj : a.getAtoms()) {
                //System.out.println("Oggetto nell'AnswerSet: " + obj.toString());
                if (obj instanceof Move move) {
                    //System.out.println();
                    //System.out.println("Direzione trovata: " + move.getDirection());
                    //System.out.println();
                    ultimaDirezione= move.getDirection();
                    return move.getDirection();
                }
            }
        }

        //System.out.println("Nessuna direzione valida trovata nei risultati ASP.");
        return 0;
    }



    public static int bfs(Block[][] matrix, int startX, int startY, int playerHead) {
        return BFSPathFinder.bfs(matrix, startX, startY, playerHead);
    }

    private int calculateFreeSpace(Block[][] matrix, int startX, int startY) {
        int freeSpace = 0;
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int x = cell[0];
            int y = cell[1];
            freeSpace++;

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (nx >= 0 && nx < matrix.length && ny >= 0 && ny < matrix[0].length && !visited[nx][ny] && matrix[nx][ny].type() == Block.EMPTY) {
                    queue.add(new int[]{nx, ny});
                    visited[nx][ny] = true;
                }
            }
        }
        return freeSpace;
    }

    public static int calculateRiskTrap(Block[][] matrix, int startX, int startY) {
        int risk = 0;
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int x = cell[0];
            int y = cell[1];

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (nx >= 0 && nx < matrix.length && ny >= 0 && ny < matrix[0].length) {
                    if (matrix[nx][ny].type() != Block.EMPTY) {
                        risk++;
                    } else if (!visited[nx][ny]) {
                        queue.add(new int[]{nx, ny});
                        visited[nx][ny] = true;
                    }
                }
            }
        }
        return risk;
    }


    public List<Node> percorso(Block blocks[][], int playerPositionX, int playerPositionY, int num1, int num2, HashSet<Node> closedList){
        //System.out.println("Percorso da: "+ playerPositionX+" "+playerPositionY+" A "+num1+" "+num2);
        Node start = new Node(playerPositionX, playerPositionY);
        Node goal = new Node(num1, num2);
        return findPath(blocks, start, goal, closedList);
    }

    public int  calcolaDirezione(Node node, int playerPositionX, int playerPositionY){
        int x = node.x;
        int y = node.y;

        if(playerPositionX +1 == x && playerPositionY == y){
            return 0;
        }
        if(playerPositionX -1 == x && playerPositionY == y){
            return 1;
        }
        if(playerPositionX == x && playerPositionY -1 == y){
            return 2;
        }
        return 3;
    }


    public int[] getGoal(Block[][] blocks ){
        Random random = new Random();
        int[] lista = new int[2];
        int num1, num2;

        do{
            num1 = random.nextInt(64);
            num2 = random.nextInt(40);

        }while(blocks[num1][num2].type() != Block.EMPTY);


        lista[0] = num1;
        lista[1] = num2;


        return lista;

    }
}
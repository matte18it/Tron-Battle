package org.application.IA.IA_Palkia.Research.CloseEnemy;

import org.application.IA.IA_Palkia.Research.AStar.AStar;
import org.application.IA.IA_Palkia.Research.BFS.BreadthFirstSearch;
import org.application.IA.IA_Palkia.Research.Support.Cell;
import org.application.IA.IA_Palkia.Utility.DataClass;
import org.application.model.Block;

import java.util.ArrayList;
import java.util.List;

public class CloseEnemy {
    private static CloseEnemy closeEnemy;

    private CloseEnemy() {}
    public static CloseEnemy getInstance() {
        if (closeEnemy == null) {
            closeEnemy = new CloseEnemy();
        }
        return closeEnemy;
    }

    public Boolean isEnemyCloseable(Block[][] blocks, int depth, int playerHead){
        int lunghezzaMax = depth*3;
        List<List> pathTrovati = new ArrayList<>();
        //creo una copia di blocks
        Block[][] blocksCopy = new Block[blocks.length][blocks[0].length];
        for(int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                blocksCopy[i][j] = blocks[i][j];
            }
        }

        Cell enemy = DataClass.getInstance().getEnemyCoordinates();
        //trovo la posizione del mio player
        Cell player = new Cell(0, 0, 0);
        for(int i = 0; i < blocks.length; i++){
            for(int j = 0; j < blocks[0].length; j++){
                if(blocks[i][j].type() == playerHead){
                    player.x = i;
                    player.y = j;
                }
            }
        }
        //sostituisco al mio player un muro
        blocksCopy[player.x][player.y] = new Block(8);

        for(int j=1; j<=depth; j++){
            if(pathTrovati.size() > 0){
                break;
            }
            List<List<Cell>> paths = new ArrayList<>();
            for(int i=(-j+1); i<=(j-1); i++) {
                //cerca a quadrato espandendo da playerx e playery
                if(player.x+j >= 0 && player.x+j < blocks.length && player.y+i >= 0 && player.y+i < blocks[0].length && blocks[player.x+j][player.y+i].type() == 0){
                    paths.add(AStar.findPath(blocksCopy, player, new Cell(player.x+j, player.y+i, 0), lunghezzaMax));
                }
                if(player.x-j >= 0 && player.x-j < blocks.length && player.y+i >= 0 && player.y+i < blocks[0].length && blocks[player.x-j][player.y+i].type() == 0){
                    paths.add(AStar.findPath(blocksCopy, player, new Cell(player.x-j, player.y+i, 0), lunghezzaMax));
                }
                if(player.x+i >= 0 && player.x+i < blocks.length && player.y+j >= 0 && player.y+j < blocks[0].length && blocks[player.x+i][player.y+j].type() == 0){
                    paths.add(AStar.findPath(blocksCopy, player, new Cell(player.x+i, player.y+j, 0), lunghezzaMax));
                }
                if(player.x+i >= 0 && player.x+i < blocks.length && player.y-j >= 0 && player.y-j < blocks[0].length && blocks[player.x+i][player.y-j].type() == 0){
                    paths.add(AStar.findPath(blocksCopy, player, new Cell(player.x+i, player.y-j, 0), lunghezzaMax));
                }
            }
            //cerca i 4 angoli
            if(player.x+j >= 0 && player.x+j < blocks.length && player.y+j >= 0 && player.y+j < blocks[0].length && blocks[player.x+j][player.y+j].type() == 0){
                paths.add(AStar.findPath(blocksCopy, player, new Cell(player.x+j, player.y+j, 0), lunghezzaMax));
            }
            if(player.x+j >= 0 && player.x+j < blocks.length && player.y-j >= 0 && player.y-j < blocks[0].length && blocks[player.x+j][player.y-j].type() == 0){
                paths.add(AStar.findPath(blocksCopy, player, new Cell(player.x+j, player.y-j, 0), lunghezzaMax));
            }
            if(player.x-j >= 0 && player.x-j < blocks.length && player.y+j >= 0 && player.y+j < blocks[0].length && blocks[player.x-j][player.y+j].type() == 0){
                paths.add(AStar.findPath(blocksCopy, player, new Cell(player.x-j, player.y+j, 0), lunghezzaMax));
            }
            if(player.x-j >= 0 && player.x-j < blocks.length && player.y-j >= 0 && player.y-j < blocks[0].length && blocks[player.x-j][player.y-j].type() == 0){
                paths.add(AStar.findPath(blocksCopy, player, new Cell(player.x-j, player.y-j, 0), lunghezzaMax));
            }

            if (paths.size() == 0){
                continue;
            }
            for(List<Cell> path : paths) {
                if(path == null){
                    continue;
                }
                //inserisci il path in blocksCopy
                for (Cell p : path) {
                    blocksCopy[p.x][p.y] = new Block(6);
                }

                //trova le celle adiacenti libere all'ultima inserita (definisco i due spazi del taglio)
                Cell last = path.get(path.size() - 1);
                List<Cell> celleAdiacentiTaglio = new ArrayList<>();
                for (int[] dir : AStar.directions) {
                    int x = last.x + dir[0];
                    int y = last.y + dir[1];
                    if (x >= 0 && x < blocks.length && y >= 0 && y < blocks[0].length) {
                        if (blocksCopy[x][y].type() == 0) {
                            celleAdiacentiTaglio.add(new Cell(x, y, 0));
                        }
                    }
                }

                //calcola le celle raggiungibili dalle celle adiacenti al taglio
                List<Integer> dimTagli = new ArrayList<>();
                int dimMinore = Integer.MAX_VALUE;
                int dimMaggiore = Integer.MIN_VALUE;
                for(int i=0; i<celleAdiacentiTaglio.size(); i++){
                    int tmp = (BreadthFirstSearch.getInstance().indirectCell(blocksCopy, celleAdiacentiTaglio.get(i)));
                    dimTagli.add(tmp);
                    if (tmp<dimMinore){
                        dimMinore = tmp;
                    }
                    if (tmp>dimMaggiore){
                        dimMaggiore = tmp;
                    }
                }

                if(celleAdiacentiTaglio.size() > 1) {
                    List<Cell> celleRaggiungibiliNemico = new ArrayList<>();
                    for (Cell celleAdi : celleAdiacentiTaglio) {
                        if ((AStar.findPath(blocksCopy, celleAdi, enemy, -1)) != null) {
                            celleRaggiungibiliNemico.add(celleAdi);
                        }
                    }

                    //controlla che il nemico possa raggiungere almeno una ma non tutte le celle tagliate
                    boolean raggiungeUna=false;
                    boolean nonRaggiungeUna=false;

                    for (Cell celleAdi : celleAdiacentiTaglio) {
                        if ((AStar.findPath(blocksCopy, celleAdi, enemy, -1)) != null) {
                            raggiungeUna = true;
                        } else {
                            nonRaggiungeUna = true;
                        }
                    }

                    List<Integer> aree = new ArrayList<>();
                    if (raggiungeUna && nonRaggiungeUna) {
                        //calcola le aree
                        for (Cell celleAdi : celleAdiacentiTaglio) {
                            aree.add(BreadthFirstSearch.getInstance().indirectCell(blocksCopy, celleAdi));
                        }
                        int areaNemico = (BreadthFirstSearch.getInstance().indirectCell(blocksCopy, celleRaggiungibiliNemico.get(0)));
                        //controlla che l'area del nemico sia minore di tutte le aree
                        boolean areaMinore = true;
                        for (int area : aree) {
                            if (area < areaNemico) {
                                areaMinore = false;
                            }
                        }
                        if (areaMinore){
                            //stampa matrice
                            int camminoNemicoAObiettivo = AStar.findPath(blocks, enemy, path.get(path.size()-1), -1).size();
                            if(camminoNemicoAObiettivo > path.size()){
                                pathTrovati.add(path);
                            }
                        }
                    }

                }

                //ripristina la matrice
                for (Cell p : path) {
                    blocksCopy[p.x][p.y] = new Block(0);
                }
            }
        }

        if(pathTrovati.size() > 0){
            //scorri tutti i path trovvati e prendi quello più breve
            List<Cell> pathMin = null;
            int min = Integer.MAX_VALUE;
            for(List<Cell> path : pathTrovati){
                if(path.size() < min){
                    pathMin = path;
                    min = path.size();
                }
            }

            //inserisci il path in blocks
            for (Cell p : pathMin) {
                blocksCopy[p.x][p.y] = new Block(6);
            }

            //togli il primo elemento del path
            pathMin.remove(0);

            DataClass.getInstance().setPathChiusura(pathMin);
            return true;
        }


        DataClass.getInstance().getPathChiusura().clear();
        return false;
    }
}
package org.application.IA.IA_Palkia.Research.BFS;

import org.application.IA.IA_Palkia.Research.Support.Cell;
import org.application.model.Block;
import org.application.utility.Settings;

import java.util.*;

// Classe che implementa la ricerca in ampiezza (BFS)
public class BreadthFirstSearch {
    // ----- ATTRIBUTE -----
    private static BreadthFirstSearch instance = null;
    private static int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // Direzioni di movimento (giù, su, destra, sinistra)

    // ----- CONSTRUCTOR -----
    private BreadthFirstSearch() {}
    public static BreadthFirstSearch getInstance() {
        if (instance == null) {
            instance = new BreadthFirstSearch();
        }
        return instance;
    }

    // ----- METHODS -----
    // BFS algorithm to find the distance between two points
    public int searchStartEnd(Block[][] blocks, Cell start, Cell end) {
        int rows = blocks.length; // Righe corrispondono alla dimensione esterna dell'array
        int cols = blocks[0].length; // Colonne corrispondono alla dimensione interna dell'array

        boolean[][] visited = new boolean[rows][cols];
        Queue<Cell> queue = new LinkedList<>();
        queue.add(new Cell(start.x, start.y, 0));
        visited[start.x][start.y] = true;

        int distance = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Cell current = queue.poll();

                // Se il punto corrente è il punto finale, ritorna la distanza
                if (current.x == end.x && current.y == end.y) {
                    return distance;
                }

                for (int[] direction : directions) {
                    int newX = current.x + direction[0];
                    int newY = current.y + direction[1];

                    // Controlla se il nuovo punto è all'interno dei limiti e non è stato visitato
                    if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && !visited[newX][newY] &&
                            (blocks[newX][newY].type() == Block.EMPTY || (newX == end.x && newY == end.y))) {
                        queue.add(new Cell(newX, newY, 0));
                        visited[newX][newY] = true;
                    }
                }
            }
            distance++;
        }
        return -1; // Path not found
    }

    // BFS to define the indirect cell
    public int indirectCell(Block[][] blocks, Cell start) {
        // Per ogni mossa presente in nextCell va a calcolare quante celle indirettamente connesse ci sono con l'obiettivo di prendere la strada che gli permette di rimanere in vita il più a lungo possibile
        Queue<Cell> queue = new LinkedList<>();
        List<Cell> nextCell = new ArrayList<>();
        int count = 0;

        nextCell.add(start);

        for (Cell cell : nextCell) {
            boolean[][] visited = new boolean[Settings.WORLD_SIZEX][Settings.WORLD_SIZEY];
            queue.offer(cell);
            visited[cell.x][cell.y] = true;
            count = 0;

            while (!queue.isEmpty()) {
                Cell current = queue.poll();

                for (int[] direction : directions) {
                    int newX = current.x + direction[0];
                    int newY = current.y + direction[1];

                    if (newX >= 0 && newX < Settings.WORLD_SIZEX && newY >= 0 && newY < Settings.WORLD_SIZEY
                            && blocks[newX][newY].type() == 0 && !visited[newX][newY]) {
                        visited[newX][newY] = true;
                        queue.offer(new Cell(newX, newY, 0));
                        count++;
                    }
                }
            }
        }
        return count;
    }
    public int indirectCell(Block[][] blocks, Cell start, int maxDepth) {
        // Per ogni mossa presente in nextCell va a calcolare quante celle indirettamente connesse ci sono
        Queue<Cell> queue = new LinkedList<>();
        boolean[][] visited = new boolean[Settings.WORLD_SIZEX][Settings.WORLD_SIZEY];
        queue.offer(new Cell(start.x, start.y, 0));  // Aggiungo il terzo campo per la profondità
        visited[start.x][start.y] = true;
        int count = 0;

        while (!queue.isEmpty()) {
            Cell current = queue.poll();

            if (current.c > maxDepth) { // Controllo la profondità
                continue; // Se supera la profondità massima, non continua a esplorare da questo nodo
            }

            for (int[] direction : directions) {
                int newX = current.x + direction[0];
                int newY = current.y + direction[1];

                if (newX >= 0 && newX < Settings.WORLD_SIZEX && newY >= 0 && newY < Settings.WORLD_SIZEY
                        && blocks[newX][newY].type() == 0 && !visited[newX][newY]) {
                    visited[newX][newY] = true;
                    queue.offer(new Cell(newX, newY, current.c + 1));  // Incremento la profondità
                    count++;
                }
            }
        }
        return count;
    }

    // BFS to define if an enemy is present
    public boolean thisIsEnemy(Block[][] blocks, Cell start, int depthLimit, int playerHead) {
        // Inizializza la coda per BFS
        Queue<Cell> queue = new LinkedList<>();
        boolean[][] visited = new boolean[Settings.WORLD_SIZEX][Settings.WORLD_SIZEY];

        // Offre il punto di inizio alla coda con profondità iniziale 0
        queue.offer(new Cell(start.x, start.y, 0));
        visited[start.x][start.y] = true;

        while (!queue.isEmpty()) {
            Cell current = queue.poll();

            // Se si raggiunge la profondità massima, interrompe l'esplorazione di questo nodo
            if (current.c > depthLimit) {
                continue;
            }

            // Esplora tutte le direzioni possibili
            for (int[] direction : directions) {
                int newX = current.x + direction[0];
                int newY = current.y + direction[1];

                // Controlla i limiti e se la cella non è stata ancora visitata
                if (newX >= 0 && newX < Settings.WORLD_SIZEX && newY >= 0 && newY < Settings.WORLD_SIZEY
                        && !visited[newX][newY]) {
                    visited[newX][newY] = true;

                    // Verifica se il tipo del blocco corrisponde a un nemico
                    if ((blocks[newX][newY].type() == Block.PLAYER1_HEAD && blocks[newX][newY].type() != playerHead) || (blocks[newX][newY].type() == Block.PLAYER2_HEAD && blocks[newX][newY].type() != playerHead) || (blocks[newX][newY].type() == Block.PLAYER3_HEAD && blocks[newX][newY].type() != playerHead) || (blocks[newX][newY].type() == Block.PLAYER4_HEAD && blocks[newX][newY].type() != playerHead)) {
                        return true;  // Se trova un nemico, restituisce true
                    }

                    // Se non è un nemico, lo aggiunge alla coda con la profondità incrementata
                    queue.offer(new Cell(newX, newY, current.c + 1));
                }
            }
        }

        return false;  // Se la ricerca termina senza trovare nemici, restituisce false
    }
}

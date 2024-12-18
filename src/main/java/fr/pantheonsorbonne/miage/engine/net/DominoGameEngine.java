package fr.pantheonsorbonne.miage.engine.net;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.pantheonsorbonne.miage.model.Game;

public abstract class DominoGameEngine {

    protected final Game game;                  
    protected final List<String> players;          
    protected final Map<String, List<String>> hands; 
    protected String currentPlayer;              

    /**
     * @param game    
     * @param players
     */
    public DominoGameEngine(Game game, List<String> players) {
        this.game = game;
        this.players = new ArrayList<>(players);
        this.hands = new HashMap<>();
        this.currentPlayer = players.get(0); 
    }

    public void initializeGame() {
        System.out.println("Initialisation de la partie de domino");

        List<String> dominoSet = generateDominoSet(); 
        Collections.shuffle(dominoSet);

        int handSize = dominoSet.size() / players.size();
        for (String player : players) {
            hands.put(player, new ArrayList<>(dominoSet.subList(0, handSize)));
            dominoSet.subList(0, handSize).clear();
            sendHandToPlayer(player, hands.get(player));
        }

        System.out.println("Dominos distribués !");
    }

    public void playGame() {
        System.out.println("Début de la partie de domino !");

        while (!isGameOver()) {
            System.out.println("Tour du joueur : " + currentPlayer);

            String move = requestMoveFromPlayer(currentPlayer);

            if (isValidMove(move)) {
                processMove(currentPlayer, move);
                System.out.println("Mouvement joué : " + move);
            } else {
                System.out.println("Mouvement invalide. Tour sauté pour " + currentPlayer);
            }

            currentPlayer = getNextPlayer();
        }

        String winner = determineWinner();
        declareWinner(winner);
    }

    protected List<String> generateDominoSet() {
        List<String> dominoes = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                dominoes.add(i + "|" + j);
            }
        }
        return dominoes;
    }

    protected String getNextPlayer() {
        int index = players.indexOf(currentPlayer);
        return players.get((index + 1) % players.size());
    }

    protected boolean isGameOver() {
        for (List<String> hand : hands.values()) {
            if (hand.isEmpty()) {
                return true; 
            }
        }
        return false;
    }

    protected String determineWinner() {
        for (Map.Entry<String, List<String>> entry : hands.entrySet()) {
            if (entry.getValue().isEmpty()) {
                return entry.getKey();
            }
        }
        return "Aucun gagnant détecté.";
    }

    protected abstract void sendHandToPlayer(String player, List<String> hand);

    protected abstract String requestMoveFromPlayer(String player);

    protected abstract void processMove(String player, String move);

    protected abstract void declareWinner(String winner);

    protected boolean isValidMove(String move) {
        return move != null && move.matches("\\d\\|\\d");
    }
}
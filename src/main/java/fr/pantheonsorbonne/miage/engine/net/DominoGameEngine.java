package fr.pantheonsorbonne.miage.engine.net;

import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

import java.util.*;

/**
 * Classe abstraite représentant la logique du jeu de domino.
 */
public abstract class DominoGameEngine {

    protected final Game game;                      // Partie en cours
    protected final List<String> players;           // Liste des joueurs
    protected final Map<String, List<String>> hands; // Mains des joueurs (leurs dominos)
    protected String currentPlayer;                // Joueur actuel

    /**
     * Constructeur.
     *
     * @param game    La partie.
     * @param players Les joueurs participant.
     */
    public DominoGameEngine(Game game, List<String> players) {
        this.game = game;
        this.players = new ArrayList<>(players);
        this.hands = new HashMap<>();
        this.currentPlayer = players.get(0); // Commence avec le premier joueur
    }

    /**
     * Initialise la partie en générant et distribuant les dominos.
     */
    public void initializeGame() {
        System.out.println("Initialisation de la partie de domino...");

        List<String> dominoSet = generateDominoSet(); // Crée les dominos de 0|0 à 6|6
        Collections.shuffle(dominoSet);

        // Distribution équitable des dominos
        int handSize = dominoSet.size() / players.size();
        for (String player : players) {
            hands.put(player, new ArrayList<>(dominoSet.subList(0, handSize)));
            dominoSet.subList(0, handSize).clear();
            sendHandToPlayer(player, hands.get(player));
        }

        System.out.println("Dominos distribués !");
    }

    /**
     * Lance la boucle principale du jeu.
     */
    public void playGame() {
        System.out.println("Début de la partie de domino !");

        while (!isGameOver()) {
            System.out.println("Tour du joueur : " + currentPlayer);

            // Demander un mouvement au joueur
            String move = requestMoveFromPlayer(currentPlayer);

            if (isValidMove(move)) {
                processMove(currentPlayer, move);
                System.out.println("Mouvement joué : " + move);
            } else {
                System.out.println("Mouvement invalide. Tour sauté pour " + currentPlayer);
            }

            currentPlayer = getNextPlayer();
        }

        // Fin de la partie
        String winner = determineWinner();
        declareWinner(winner);
    }

    /**
     * Génère tous les dominos (0|0 à 6|6).
     */
    protected List<String> generateDominoSet() {
        List<String> dominoes = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                dominoes.add(i + "|" + j);
            }
        }
        return dominoes;
    }

    /**
     * Retourne le prochain joueur.
     */
    protected String getNextPlayer() {
        int index = players.indexOf(currentPlayer);
        return players.get((index + 1) % players.size());
    }

    /**
     * Vérifie si le jeu est terminé (si un joueur n'a plus de dominos).
     */
    protected boolean isGameOver() {
        for (List<String> hand : hands.values()) {
            if (hand.isEmpty()) {
                return true; // Un joueur a gagné
            }
        }
        return false;
    }

    /**
     * Détermine le gagnant.
     */
    protected String determineWinner() {
        for (Map.Entry<String, List<String>> entry : hands.entrySet()) {
            if (entry.getValue().isEmpty()) {
                return entry.getKey();
            }
        }
        return "Aucun gagnant détecté.";
    }

    /**
     * Méthodes abstraites à implémenter dans les classes filles (Host/Client).
     */
    protected abstract void sendHandToPlayer(String player, List<String> hand);

    protected abstract String requestMoveFromPlayer(String player);

    protected abstract void processMove(String player, String move);

    protected abstract void declareWinner(String winner);

    /**
     * Vérifie si un mouvement est valide (format 0|0 à 6|6).
     */
    protected boolean isValidMove(String move) {
        return move != null && move.matches("\\d\\|\\d");
    }
}


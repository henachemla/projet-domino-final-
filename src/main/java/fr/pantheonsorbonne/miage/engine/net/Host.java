package fr.pantheonsorbonne.miage.engine.net;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.HostFacade;
import fr.pantheonsorbonne.miage.engine.net.DominoGameEngine;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

import java.util.ArrayList;
import java.util.List;

public class Host extends DominoGameEngine {

    private final HostFacade hostFacade;

    public Host(Game game, List<String> players, HostFacade hostFacade) {
        super(game, players);
        this.hostFacade = hostFacade;
    }

    public static void main(String[] args) {
        try {
            System.out.println("Initialisation du serveur...");
            HostFacade facade = (HostFacade) Facade.getFacade();
            facade.waitReady();

            // Création de la partie
            System.out.println("Création d'une nouvelle partie de Domino...");
            Game game = facade.createNewGame("DominoMultijoueur");
            System.out.println("Jeu créé avec succès : " + game);

            // Définir le nom de l'hôte
            String hostName = "Host";
            facade.createNewPlayer(hostName);
            System.out.println("Nom de l'hôte défini : " + hostName);

            // Attente des joueurs
            System.out.println("En attente de joueurs...");
            facade.waitForExtraPlayerCount(1); // 1 joueur minimum en plus de l'hôte

            List<String> players = new ArrayList<>();
            players.add("Host"); // car methode pour recup les joueurs n'existe pas
            players.addAll(game.getPlayers());
            System.out.println("Joueurs connectés :" +players);

            Host host = new Host(game, players, facade);
            host.initializeGame();
            System.out.println("La partie commence !");
            facade.sendGameCommandToAll(game, new GameCommand("START", "Le jeu commence !"));
            game.setState(Game.GameState.STARTED);

            host.playGame();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void sendHandToPlayer(String player, List<String> hand) {
        System.out.println("Envoi des dominos à " + player);
        hostFacade.sendGameCommandToPlayer(game, player, new GameCommand("hand", String.join(",", hand)));
    }

    @Override
    protected String requestMoveFromPlayer(String player) {
        System.out.println("En attente du mouvement de : " + player);
        GameCommand command = hostFacade.receiveGameCommand(game);
        return command.body();
    }

    @Override
    protected void processMove(String player, String move) {
        System.out.println("Mouvement validé pour " + player + " : " + move);
        // Ici, tu peux ajouter la logique pour gérer les dominos posés
    }

    @Override
    protected void declareWinner(String winner) {
        System.out.println("Le joueur " + winner + " a gagné !");
        hostFacade.sendGameCommandToAll(game, new GameCommand("gameOver", "Le joueur " + winner + " a gagné !"));
    }
}

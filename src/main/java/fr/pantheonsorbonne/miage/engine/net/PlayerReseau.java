package fr.pantheonsorbonne.miage.engine.net;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

public class PlayerReseau {

    public static void main(String[] args) {
        try {
            System.out.println("Initialisation du client");
            PlayerFacade facade = (PlayerFacade) Facade.getFacade();
            facade.waitReady();

            String playerName = "Joueur" ;
            facade.createNewPlayer(playerName);
            System.out.println("Nouveau joueur créé : " + playerName);

            System.out.println("Tentative de rejoindre la partie");
            Game game = facade.autoJoinGame("DominoMultijoueur");

            System.out.println("Rejoint la partie : " + game.getGameName());

            List<String> hand = new ArrayList<>();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("En attente des commandes du serveur");
                GameCommand command = facade.receiveGameCommand(game);

                switch (command.name()) {
                    case "hand":
                        System.out.println("Votre main de dominos : " + command.body());
                        for (String domino : command.body().split(",")) {
                            hand.add(domino);
                        }
                        break;

                    case "play":
                        System.out.println("C'est à vous de jouer !");
                        System.out.println("Votre main actuelle : " + hand);
                        System.out.print("Entrez un domino à jouer (exemple: 3|6) : ");
                        String move = scanner.nextLine();
                        if (hand.contains(move)) {
                            hand.remove(move);
                            facade.sendGameCommandToAll(game, new GameCommand("move", move));
                        } else {
                            System.out.println("Domino invalide ou déjà joué.");
                        }
                        break;

                    case "gameOver":
                        System.out.println("La partie est terminée : " + command.body());
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Commande inconnue : " + command.name());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

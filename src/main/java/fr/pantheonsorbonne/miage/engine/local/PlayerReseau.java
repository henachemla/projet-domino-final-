package fr.pantheonsorbonne.miage.engine.local;
import java.util.Scanner;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

public class PlayerReseau {
        public static void main(String[] args) {
            try {
                // Initialisation de la connexion via la librairie
                PlayerFacade facade = (PlayerFacade) Facade.getFacade();
                facade.waitReady(); // Attend que la façade soit prête
    
                // Rejoindre la partie avec le nom du jeu
                String gameName = "DominoMultijoueur";
                Game game = facade.autoJoinGame(gameName);
                System.out.println("Rejoint la partie : " + gameName);
    
                Scanner scanner = new Scanner(System.in);
    
                // Boucle pour le jeu (simplifiée)
                while (true) {
                    System.out.println("En attente de commandes du jeu...");
    
                    // Réception d'une commande du serveur
                    GameCommand command = facade.receiveGameCommand(game);
                    System.out.println("Commande reçue : " + command.name() + " -> " + command.body());
    
                    // Exécuter une action selon la commande
                    if (command.name().equals("START")) {
                        System.out.println("La partie commence !");
                    }
    
                    // Exemple d'envoi d'une commande au serveur
                    System.out.println("Entrez un mouvement (exemple: PLAY 3|6) : ");
                    String input = scanner.nextLine();
                    facade.sendGameCommandToAll(game, new GameCommand("PLAY", input, null));
    
                    // Arrêter le jeu avec une condition de sortie
                    if (input.equalsIgnoreCase("EXIT")) {
                        System.out.println("Déconnexion du jeu.");
                        break;
                    }
                }
    
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

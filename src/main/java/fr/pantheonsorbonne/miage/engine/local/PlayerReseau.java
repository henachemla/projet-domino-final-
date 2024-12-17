package fr.pantheonsorbonne.miage.engine.local;
import java.util.Scanner;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

public class PlayerReseau {
        public static void main(String[] args) {
            try {
                System.out.println("Initialisation du client...");
                PlayerFacade facade = (PlayerFacade) Facade.getFacade();
                System.out.println("Facade récupérée, attente de readiness...");
                facade.waitReady(); 
                System.out.println("Facade prête, tentative de rejoindre le jeu...");
                // Rejoindre la partie avec le nom du jeu
                String gameName = "DominoMultijoueur";
                Game game = facade.autoJoinGame("DominoMultijoueur");
                System.out.println("Rejoint la partie : " + gameName);// n'affiche plus ce message --> beug dans la connexion au jeu 
    
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
                    }else if (command.name().equals("PLAY")) {
                        System.out.println("Mouvement joué : " + command.body());
                    } else {
                        System.out.println("Commande non reconnue : " + command.name());
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
                scanner.close();
            } catch (Exception e) {
                System.err.println("Une erreur est survenue : " + e.getMessage());
                e.printStackTrace();
            }

        }
    }

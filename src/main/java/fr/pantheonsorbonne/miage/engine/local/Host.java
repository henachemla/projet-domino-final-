package fr.pantheonsorbonne.miage.engine.local;
import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.HostFacade;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

public class Host {
    public static void main(String[] args) {
        try {
            
            HostFacade facade = (HostFacade) Facade.getFacade();
            facade.waitReady(); 

            System.out.println("Création d'une nouvelle partie...");
            Game game = facade.createNewGame("DominoMultijoueur");

            System.out.println("En attente de joueurs...");
            facade.waitForExtraPlayerCount(2);

            System.out.println("La partie commence !");
            facade.sendGameCommandToAll(game, new GameCommand("start", "Le jeu commence !"));

            while (true) {
                
                GameCommand command = facade.receiveGameCommand(game);
                System.out.println("Commande reçue : " + command.body());

                facade.sendGameCommandToAll(game, command);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}


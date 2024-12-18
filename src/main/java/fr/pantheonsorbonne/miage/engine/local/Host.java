package fr.pantheonsorbonne.miage.engine.local;
import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.HostFacade;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

public class Host extends DominoGameEngine{
    public static void main(String[] args) {
        try {
            
            HostFacade facade = (HostFacade) Facade.getFacade();
            facade.waitReady(); 
            
            System.out.println("Création d'une nouvelle partie...");
            Game game = facade.createNewGame("DominoMultijoueur");
            System.out.println("Jeu créé avec succès : " + game);

            facade.createNewPlayer("Host");
            System.out.println("Nom de l'hôte défini : Host");
            game.setHostName("Host");

            System.out.println("En attente de joueurs...");
            facade.waitForExtraPlayerCount(1);

            System.out.println("La partie commence !");
            facade.sendGameCommandToAll(game, new GameCommand("START", "Le jeu commence !"));
            game.setState(Game.GameState.STARTED);
            
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


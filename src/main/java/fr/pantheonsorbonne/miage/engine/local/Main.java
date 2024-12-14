package fr.pantheonsorbonne.miage.engine.local;

import java.util.*;

/* 
import fr.pantheonsorbonne.miage.Facade;

public class Main {
    public static void main (String[] args){
        Facade facade = Facade.getFacade();
        facade.waitReady();

        Game game = new Game (facade);
        game.start();
    }

}
*/


public class Main {
    public static void main(String[] args) {
        // Initialisation des joueurs
        List<Player> joueurs = new ArrayList<>();
        joueurs.add(new Player("Alice"));
        joueurs.add(new Player("Bob"));
        joueurs.add(new Player("Charlie"));

        // Génération de tous les dominos
        List<Domino> pioche = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                pioche.add(new Domino(i, j));
            }
        }

        // Mélange de la pioche
        Collections.shuffle(pioche);

        // Distribution des dominos aux joueurs
        int dominosParJoueur = 7;
        for (Player joueur : joueurs) {
            for (int i = 0; i < dominosParJoueur; i++) {
                joueur.addDomino(pioche.remove(0));
            }
        }

        // Initialisation du plateau
        Board plateau = new Board();

        // Création et démarrage du jeu
        Game jeu = new Game(joueurs, plateau, pioche);
        jeu.demarrer();
    }
}

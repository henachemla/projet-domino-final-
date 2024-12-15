package fr.pantheonsorbonne.miage.engine.local;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        joueurs.add(new Player("Alice", PlayerType.DEFENSIF));
        joueurs.add(new Player("Bob", PlayerType.OPPORTUNISTE));
        joueurs.add(new Player("Charlie", PlayerType.AGGRESSIF));

        // Génération de tous les dominos
        List<Domino> pioche = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                String type = "standard";
                if (i == j) type = "bonus";  // Les doubles sont des "double bonus"
                if (i == 3 && j == 6) type = "bloquant"; // Exemple : domino bloquant
                if (i == 2 && j == 5) type = "dynamique"; // Exemple : domino dynamique
                pioche.add(new Domino(i, j, type));
            }
        }
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

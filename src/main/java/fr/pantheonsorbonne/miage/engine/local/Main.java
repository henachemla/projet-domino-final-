package fr.pantheonsorbonne.miage.engine.local;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Player> joueurs = new ArrayList<>();
        joueurs.add(new Player("Ben", PlayerType.DEFENSIF));
        joueurs.add(new Player("Hena", PlayerType.OPPORTUNISTE));
        joueurs.add(new Player("Margaux", PlayerType.AGGRESSIF));

        List<Domino> pioche = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                String type = "standard";
                if (i == j) type = "bonus";
                if (i == 3 && j == 6) type = "bloquant"; 
                if (i == 2 && j == 5) type = "dynamique";
                pioche.add(new Domino(i, j, type));
            }
        }
        Collections.shuffle(pioche);

        int dominosParJoueur = 7;
        for (Player joueur : joueurs) {
            for (int i = 0; i < dominosParJoueur; i++) {
                joueur.addDomino(pioche.remove(0));
            }
        }

        Board plateau = new Board();

        Game jeu = new Game(joueurs, plateau, pioche);
        jeu.demarrer();
    }
}

package fr.pantheonsorbonne.miage.engine.local;

import java.util.*;

/* 
public class Player {
    private String PlayerName;
    public Player (String PlayerName){
        this.PlayerName = PlayerName;
    }

    public String getPlayerName(){
        return PlayerName;
    }
}
*/


public class Player {

    private String nom;
    private List<Domino> main;

    public Player(String nom) {
        this.nom = nom;
        this.main = new ArrayList<>();
    }

    // Retourne le nom du joueur
    public String getName() {
        return nom;
    }

    // Retourne la main du joueur
    public List<Domino> getHand() {
        return main;
    }

    // Ajoute un domino à la main du joueur
    public void addDomino(Domino domino) {
        main.add(domino);
    }

    // Retire un domino de la main du joueur
    public void removeDomino(Domino domino) {
        main.remove(domino);
    }

    // Trouve le double le plus élevé dans la main
    public Domino getHighestDouble() {
        Domino highestDouble = null;
        for (Domino domino : main) {
            if (domino.isDouble()) {
                if (highestDouble == null || domino.getLeft() > highestDouble.getLeft()) {
                    highestDouble = domino;
                }
            }
        }
        return highestDouble;
    }

    // Trouve le domino avec la plus grande somme dans la main
    public Domino getHighestDomino() {
        Domino highestDomino = null;
        for (Domino domino : main) {
            if (highestDomino == null || domino.getTotal() > highestDomino.getTotal()) {
                highestDomino = domino;
            }
        }
        return highestDomino;
    }

    // Permet au joueur de choisir un domino valide à jouer
    public Domino chooseDomino(Board plateau) {
        for (Domino domino : main) {
            if (plateau.canPlace(domino)) {
                return domino;
            }
        }
        return null; // Aucun domino valide trouvé
    }

    // Vérifie si le joueur peut jouer un domino valide
    public boolean canPlay(Board plateau) {
        for (Domino domino : main) {
            if (plateau.canPlace(domino)) {
                return true;
            }
        }
        return false;
    }

    // Calcule les points restants dans la main du joueur
    public int calculateHandPoints() {
        int totalPoints = 0;
        for (Domino domino : main) {
            totalPoints += domino.getTotal();
        }
        return totalPoints;
    }
}

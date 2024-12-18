package fr.pantheonsorbonne.miage.engine.local;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private String nom;
    private List<Domino> main;
    private PlayerType type;

    public Player(String nom,PlayerType type){
        this.nom = nom;
        this.main = new ArrayList<>();
        this.type=type;
    }

    public String getName() {
        return nom;
    }

    public PlayerType getType() {
        return type;
    }

    public List<Domino> getHand() {
        return main;
    }

    public void addDomino(Domino domino) {
        main.add(domino);
    }

    public void removeDomino(Domino domino) {
        main.remove(domino);
    }

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

    public Domino getHighestDomino() {
        Domino highestDomino = null;
        for (Domino domino : main) {
            if (highestDomino == null || domino.getTotal() > highestDomino.getTotal()) {
                highestDomino = domino;
            }
        }
        return highestDomino;
    }

    public Domino chooseDomino(Board plateau) {
        for (Domino domino : main) {
            if (plateau.canPlace(domino)) {
                return domino;
            }
        }
        return null; 
    }

    public boolean canPlay(Board plateau) {
        for (Domino domino : main) {
            if (plateau.canPlace(domino)) {
                return true;
            }
        }
        return false;
    }

    public int calculateHandPoints() {
        int totalPoints = 0;
        for (Domino domino : main) {
            totalPoints += domino.getTotal();
        }
        return totalPoints;
    }
}

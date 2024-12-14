package fr.pantheonsorbonne.miage.engine.local;

import java.util.*;

/* 
import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.PlayerFacade;
*/

/* 
public class Game {
    private Facade facade;
    private String playerName;

    public void createNewPlayer (String playerName){
        PlayerFacade playerFacade = (PlayerFacade) Facade.getFacade();
        playerFacade.waitReady(); 

        playerFacade.createNewPlayer(playerName);

        System.out.println("Player created: " + playerName);
    }

}
    */


    public class Game {
    
        private List<Player> joueurs;
        private Board plateau;
        private List<Domino> pioche;
    
        public Game(List<Player> joueurs, Board plateau, List<Domino> pioche) {
            this.joueurs = joueurs;
            this.plateau = plateau;
            this.pioche = pioche;
        }
    
        // Détermine le joueur qui commence
        public Player determinerJoueurCommencant() {
            Player joueurCommencant = null;
            Domino doublePlusHaut = null;
    
            for (Player joueur : joueurs) {
                Domino doubleLePlusHautDuJoueurActuel = joueur.getHighestDouble();
                if (doubleLePlusHautDuJoueurActuel != null && (doublePlusHaut == null || doubleLePlusHautDuJoueurActuel.getLeft() > doublePlusHaut.getLeft())) {
                    doublePlusHaut = doubleLePlusHautDuJoueurActuel;
                    joueurCommencant = joueur;
                }
            }
    
            if (joueurCommencant == null) {
                for (Player joueur : joueurs) {
                    Domino dominoLePlusHautDuJoueurActuel = joueur.getHighestDomino();
                    if (dominoLePlusHautDuJoueurActuel != null && (doublePlusHaut == null || dominoLePlusHautDuJoueurActuel.getTotal() > doublePlusHaut.getTotal())) {
                        doublePlusHaut = dominoLePlusHautDuJoueurActuel;
                        joueurCommencant = joueur;
                    }
                }
            }
    
            return joueurCommencant;
        }
    
        // Déroulement d'un tour
        public void jouerTour(Player joueur) {
            System.out.println(joueur.getName() + " joue...");
            Domino dominoChoisi = joueur.chooseDomino(plateau);
    
            if (dominoChoisi != null) {
                plateau.placeDomino(dominoChoisi);
                joueur.removeDomino(dominoChoisi);
            } else {
                if (!pioche.isEmpty()) {
                    Domino dominoPioche = pioche.remove(0);
                    System.out.println(joueur.getName() + " pioche un domino.");
                    joueur.addDomino(dominoPioche);
                } else {
                    System.out.println(joueur.getName() + " passe son tour.");
                }
            }
        }
    
        // Vérifie si la partie est terminée
        public boolean estPartieTerminee() {
            for (Player joueur : joueurs) {
                if (joueur.getHand().isEmpty()) {
                    return true;
                }
            }
            return pioche.isEmpty() && joueurs.stream().allMatch(joueur -> !joueur.canPlay(plateau));
        }
    
        // Détermine le vainqueur
        public Player determinerVainqueur() {
            Player vainqueur = null;
            int pointsMin = Integer.MAX_VALUE;
    
            for (Player joueur : joueurs) {
                int points = joueur.calculateHandPoints();
                if (points < pointsMin) {
                    pointsMin = points;
                    vainqueur = joueur;
                }
            }
    
            return vainqueur;
        }
    
        // Lance le jeu
        public void demarrer() {
            Player joueurCommencant = determinerJoueurCommencant();
            System.out.println("Le joueur " + joueurCommencant.getName() + " commence la partie.");
    
            int indexActuel = joueurs.indexOf(joueurCommencant);
    
            while (!estPartieTerminee()) {
                Player joueurActuel = joueurs.get(indexActuel);
                jouerTour(joueurActuel);
                indexActuel = (indexActuel + 1) % joueurs.size();
            }
    
            Player vainqueur = determinerVainqueur();
            System.out.println("La partie est terminée ! Le vainqueur est " + vainqueur.getName() + ".");
        }
    }
    

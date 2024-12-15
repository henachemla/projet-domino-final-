package fr.pantheonsorbonne.miage.engine.local;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/* 
import java.util.List;
import java.util.ArrayList;

public class Board {
    private List<Domino> dominosJoués;


    public Board(){
        dominosJoués= new ArrayList<>();
    }
    public boolean Matcher(Domino domino1, Domino domino2){
        if(domino1.getGauche()== domino2.getGauche() || domino1.getGauche() == domino2.getDroite() || domino1.getDroite() == domino2.getGauche() || domino1.getDroite()== domino2.getDroite()){
            return true;
        }
        return false;
    }

    public boolean ajoutDomino(Domino domino){
        if (dominosJoués.isEmpty()){
            dominosJoués.add(domino);
            return true;
        }

        Domino dernierDomino = dominosJoués.get(dominosJoués.size()-1);
        if(){

        }
    }
}
    */


    public class Board {
    
        private List<Domino> dominos;
    
        public Board() {
            this.dominos = new ArrayList<>();
        }
    
        public boolean placeDomino(Domino domino, Player joueur) {
            if (dominos.isEmpty()) {
                // Si le plateau est vide, on ajoute directement le domino
                dominos.add(domino);
                return true;
            }
        
            // Récupère les extrémités actuelle du plateau
            Domino first = dominos.get(0);
            Domino last = dominos.get(dominos.size() - 1);
        
            // Ajouter au début du plateau
            if (domino.getRight() == first.getLeft()) {
                dominos.add(0, domino);
                return true;
            } else if (domino.getLeft() == first.getLeft()) {
                dominos.add(0, new Domino(domino.getRight(), domino.getLeft(), domino.getType())); // Inverse les valeurs
                return true;
            }
        
            // Ajouter à la fin du plateau
            if (domino.getLeft() == last.getRight()) {
                dominos.add(domino);
                return true;
            } else if (domino.getRight() == last.getRight()) {
                dominos.add(new Domino(domino.getRight(), domino.getLeft(), domino.getType())); // Inverse les valeurs
                return true;
            }
        
            // Si aucune des extrémités ne correspond, le domino ne peut pas être placé
            System.out.println(joueur.getName() + " ne peut pas placer le domino : " + domino);
            return false;
        }
        
        
        private void appliquerEffetDomino(Domino domino, Player joueur) {
            switch (domino.getType()) {
                case "dynamique":
                    if (!domino.isUtilise()) {
                        System.out.println("Domino dynamique ! " + joueur.getName() + " peut changer une valeur.");
                        // Exemple simple : Changer la valeur droite en 0
                        domino.setRight(0);
                        domino.utiliserCapacite(); // Marquer comme utilisé
                        System.out.println("Nouvelle valeur du domino : " + domino);
                    }
                    break;
        
                case "bonus":
                    int sum = getEndValuesSum();
                    if (sum % 5 == 0) {
                        System.out.println("Double Bonus ! Les extrémités totalisent un multiple de 5.");
                    }
                    break;
        
                case "bloquant":
                    System.out.println("Domino bloquant ! Le prochain joueur passe son tour.");
                    break;
        
                default:
                    break; // Standard : rien à appliquer
            }
        }
        
    
        // Vérifie si un domino peut être placé sur le plateau
        public boolean canPlace(Domino domino) {
            if (dominos.isEmpty()) {
                return true; // Premier domino peut être placé n'importe où
            }
    
            Domino first = dominos.get(0);
            Domino last = dominos.get(dominos.size() - 1);
    
            return domino.getRight() == first.getLeft() ||
                   domino.getLeft() == last.getRight() ||
                   domino.getLeft() == first.getLeft() ||
                   domino.getRight() == last.getRight();
        }
    
        // Retourne la somme des extrémités du plateau
        public int getEndValuesSum() {
            if (dominos.isEmpty()) {
                return 0;
            }
            Domino first = dominos.get(0);
            Domino last = dominos.get(dominos.size() - 1);
            return first.getLeft() + last.getRight();
        }
    
        // Retourne la liste actuelle des dominos sur le plateau
        public List<Domino> getDominos() {
            return Collections.unmodifiableList(dominos);
        }
    
        // Affiche l'état actuel du plateau
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder("Plateau: ");
            for (Domino domino : dominos) {
                builder.append(domino.toString()).append(" ");
            }
            return builder.toString().trim();
        }
    }
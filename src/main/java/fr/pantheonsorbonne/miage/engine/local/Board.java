package fr.pantheonsorbonne.miage.engine.local;

import java.util.*;


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
    
        // Ajoute un domino au plateau
        public boolean placeDomino(Domino domino) {
            if (dominos.isEmpty()) {
                dominos.add(domino);
                return true;
            }
    
            Domino first = dominos.get(0);
            Domino last = dominos.get(dominos.size() - 1);
    
            if (domino.getRight() == first.getLeft()) {
                dominos.add(0, new Domino(domino.getLeft(), domino.getRight()));
                return true;
            } else if (domino.getLeft() == last.getRight()) {
                dominos.add(domino);
                return true;
            } else if (domino.getLeft() == first.getLeft()) {
                dominos.add(0, new Domino(domino.getRight(), domino.getLeft()));
                return true;
            } else if (domino.getRight() == last.getRight()) {
                dominos.add(new Domino(domino.getRight(), domino.getLeft()));
                return true;
            }
    
            return false; // Domino non valide
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
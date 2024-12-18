package fr.pantheonsorbonne.miage.engine.local;

import java.util.ArrayList;
import java.util.List;


    public class Board {
    
        private List<Domino> dominos;
    
        public Board() {
            this.dominos = new ArrayList<>();
        }
        
        private void appliquerEffetBonus() {
            int sum = getEndValuesSum(); 
            if (sum % 5 == 0 && sum !=0) {
                System.out.println("Domino Bonus ! Les extrémités totalisent un multiple de 5.");
            }
        }
        public boolean placeDomino(Domino domino, Player joueur) {
            if (dominos.isEmpty()) {
                dominos.add(domino);
                appliquerEffetBonus();
                appliquerEffetDomino(domino, joueur);
                return true;
            }


        
            Domino first = dominos.get(0);
            Domino last = dominos.get(dominos.size() - 1);
        
            if (domino.getRight() == first.getLeft()) {
                dominos.add(0, domino);
                appliquerEffetBonus();
                appliquerEffetDomino(domino, joueur);
                return true;
            } else if (domino.getLeft() == first.getLeft()) {
                dominos.add(0, new Domino(domino.getRight(), domino.getLeft(), domino.getType()));
                appliquerEffetBonus();
                appliquerEffetDomino(domino, joueur);
                return true;
            }
        
            if (domino.getLeft() == last.getRight()) {
                dominos.add(domino);
                appliquerEffetBonus();
                appliquerEffetDomino(domino, joueur);
                return true;
            } else if (domino.getRight() == last.getRight()) {
                dominos.add(new Domino(domino.getRight(), domino.getLeft(), domino.getType())); 
                appliquerEffetBonus();
                appliquerEffetDomino(domino, joueur);
                return true;
            }
        
            System.out.println(joueur.getName() + " ne peut pas placer le domino : " + domino);
            return false;
        }
        
        
        private void appliquerEffetDomino(Domino domino, Player joueur) {
            switch (domino.getType()) {
                case "dynamique":
                    if (!domino.isUtilise()) {
                        System.out.println("Domino dynamique ! " + joueur.getName() + " peut changer une valeur.");
                        domino.setRight(0);
                        domino.utiliserCapacite(); 
                        System.out.println("Nouvelle valeur du domino : " + domino);
                    }
                    break;
        
                case "bloquant":
                    System.out.println("Domino bloquant ! Le prochain joueur passe son tour.");
                    break;
        
                default:
                    break;
            }
        }
        
    
        public boolean canPlace(Domino domino) {
            if (dominos.isEmpty()) {
                return true; 
            }
    
            Domino first = dominos.get(0);
            Domino last = dominos.get(dominos.size() - 1);
    
            return domino.getRight() == first.getLeft() ||
                   domino.getLeft() == last.getRight() ||
                   domino.getLeft() == first.getLeft() ||
                   domino.getRight() == last.getRight();
        }
    
        public int getEndValuesSum() {
            if (dominos.isEmpty()) {
                return 0;
            }
            Domino first = dominos.get(0);
            Domino last = dominos.get(dominos.size() - 1);
            return first.getLeft() + last.getRight();
        }
    
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder("Plateau: ");
            for (Domino domino : dominos) {
                builder.append(domino.toString()).append(" ");
            }
            return builder.toString().trim();
        }
    }
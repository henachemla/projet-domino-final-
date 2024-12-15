package fr.pantheonsorbonne.miage.engine.local;


/* 
import java.util.ArrayList;
import java.util.List;

public class Domino {

    private int gauche;
    private int droite;

    public Domino(int gauche, int droite){
        this.gauche=gauche;
        this.droite=droite;
    }

    public int getGauche(){
        return gauche;
    }
    public int getDroite(){
        return droite;
    }
    public static List<Domino> GenerationDominos(){
        List <Domino> domino = new ArrayList<>();
        for (int i=1;i<7;i++){
            for (int j=i; j<7; j++){
                domino.add(new Domino(i,j));
            }
        }
        return domino;
    }
}
*/

    public class Domino {

        private int left;
        private int right;
        private String type;
        private boolean utilise;
    
        public Domino(int left, int right, String type) {
            this.left = left;
            this.right = right;
            this.type = (type == null) ? "standard" : type;
            this.utilise = false;
        }
    
        // Retourne la valeur gauche du domino
        public int getLeft() {
            return left;
        }
    
        // Retourne la valeur droite du domino
        public int getRight() {
            return right;
        }

        public String getType() {
            return type;
        }

        public boolean isUtilise() {
            return utilise;
        }

        public void setLeft(int left) {
            this.left = left;
        }
    
        public void setRight(int right) {
            this.right = right;
        }

        public void utiliserCapacite() {
            if (type.equals("dynamique") && !utilise) {
                this.utilise = true;
            }
        }
    
    
        // Retourne la somme des deux valeurs
        public int getTotal() {
            return left + right;
        }
    
        // Vérifie si le domino est un double
        public boolean isDouble() {
            return left == right;
        }
    
        // Vérifie si ce domino peut se connecter avec un autre domino
        public boolean canConnect(Domino other) {
            return this.left == other.left || this.left == other.right ||
                   this.right == other.left || this.right == other.right;
        }
    
        // Affiche le domino sous forme lisible
        @Override
        public String toString() {
            return "[" + left + "|" + right + "]";
        }
    
        // Vérifie si deux dominos sont égaux
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Domino domino = (Domino) obj;
            return (left == domino.left && right == domino.right) ||
                   (left == domino.right && right == domino.left);
        }
    
        // Génère un hashcode pour le domino
        @Override
        public int hashCode() {
            return left + right; // Simple addition pour garantir la commutativité
        }
    }




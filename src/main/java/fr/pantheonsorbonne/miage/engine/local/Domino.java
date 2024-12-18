package fr.pantheonsorbonne.miage.engine.local;

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
    
        public int getLeft() {
            return left;
        }
    
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
    
            public int getTotal() {
            return left + right;
        }
    
        public boolean isDouble() {
            return left == right;
        }
    
            @Override
        public String toString() {
            return "[" + left + "|" + right + "]";
        }
    
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Domino domino = (Domino) obj;
            return (left == domino.left && right == domino.right) ||
                   (left == domino.right && right == domino.left);
        }
    
        @Override
        public int hashCode() {
            return left + right; 
        }
    }




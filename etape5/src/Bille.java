public class Bille {
    private int ligne;
    private int colonne;
    private int couleur;
    private boolean vu;


    public Bille(int ligne, int colonne,int couleur){
        this.couleur=couleur;
        this.ligne=ligne;
        this.colonne=colonne;
    }
    public int getLigne(){
        return ligne;
    }
    public int getColonne(){
        return colonne;
    }

    public int getCouleur(){
        return couleur;
    }
    public void setCouleur(int coul){
        couleur = coul;
    }
    public boolean getVu(){
        return vu;
    }
    public void setVuVrai(){
        vu = true;
    }
    public void setVuFaux(){
        vu = false;
    }
}

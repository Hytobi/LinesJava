public class Bille {
    private final int ligne;
    private final int colonne;
    private int couleur;
    private boolean vu;
	private Bille[] voisins;


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
    public Bille[] getVoisins() {
		return voisins;
	}

	public void setVoisins(Bille[] voisins) {
		this.voisins = voisins;
	}

    /**Méthode qui trouve si un chemin libre existe entre la bille et la bille aux coordonnées (ligA, colA).
	 * @param ligA : La ligne de la bille d'arrivée
	 * @param colA : La colonne de la bille d'arrivée
	 * @return : vrai si un chemin existe, faux sinon
	 */
	public boolean trouverChemin(int ligA, int colA) {
		if (ligne == ligA && colonne == colA){
			return true;
        }
		vu = true;
		for (Bille bille : voisins) {
			if (bille != null) {
				if ((!bille.vu) && (bille.couleur == -1) && (bille.trouverChemin(ligA, colA))){
					return true;
                }
				bille.vu = true;
			}
		}
		return false;
	}
}
